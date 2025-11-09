package org.rio.processing;

import org.rio.common.NamedThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ShardsProcessor implements Processor {

    private final int numberOfShards;
    private final ExecutorService[] executors;
    private final CommandHandler commandHandler;
    private final Validator validator;

    private volatile boolean started = false;
    private final AtomicInteger circularIndex;

    public ShardsProcessor(int numberOfShards, CommandHandler commandHandler, Validator validator) {

        this.numberOfShards = numberOfShards;
        this.executors = new ExecutorService[numberOfShards];
        this.commandHandler = commandHandler;
        this.validator = validator;
        this.circularIndex = new AtomicInteger();
    }

    @Override
    public Future<String> process(String line) {

        if (!started) {
            throw new RuntimeException("Processor is not started");
        }

        String trimLine = line.trim();

        validator.validate(trimLine);

        int hash = hashKey(trimLine);
        if (hash == -1) {
            throw new IllegalArgumentException("-ERR invalid key");
        }

        return executors[hash].submit(() -> commandHandler.handle(trimLine));
    }

    private int hashKey(String line) {

        int separator = line.indexOf(' ');
        if (separator == -1) {
            circularIndex.set(circularIndex.incrementAndGet() % numberOfShards);
            return circularIndex.get();
        }

        int nextSeparator = line.indexOf(' ', separator + 1);
        String key;
        if (nextSeparator == -1) {
            key = line.substring(separator + 1);
        } else {
            key = line.substring(separator + 1, nextSeparator);
        }

        if (key.isBlank()) {
            return -1;
        }

        return (key.hashCode() & 0x7fffffff) % numberOfShards;
    }

    @Override
    public void start() {

        if (started) {
            return;
        }

        for (int index = 0; index < numberOfShards; index++) {
            executors[index] = Executors.newSingleThreadExecutor(new NamedThreadFactory("ShardsProcessor-" + index));
        }

        started = true;
    }

    @Override
    public void stop() {

        for (ExecutorService executorService : executors) {
            if (executorService != null) {
                stopExecutor(executorService);
            }
        }

        started = false;
    }

    private void stopExecutor(ExecutorService service) {

        try {
            service.shutdown();
            boolean success = service.awaitTermination(1, TimeUnit.SECONDS);
            if (!success) {
                service.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            service.shutdownNow();
        }
    }
}
