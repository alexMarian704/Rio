package org.rio.server;

import org.rio.processing.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Coordinator {

    private final ExecutorService executorService;
    private final Processor processor;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Coordinator(Processor processor) {

        this.processor = processor;
        this.executorService = Executors.newVirtualThreadPerTaskExecutor();
    }

    public void coordinateConnection(Socket socket) {

        executorService.submit(() -> {
            logger.info("Connection from {}", socket.getRemoteSocketAddress());

            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String line;
                while ((line = in.readLine()) != null) {
                    String response;
                    try {
                        response = processor.process(line).get();
                    } catch (Exception e) {
                        logger.warn("Error while processing", e);
                        response = e.getMessage();
                    }

                    if ("__QUIT__".equals(response)) {
                        logger.info("{} disconnected", socket.getRemoteSocketAddress());
                        break;
                    }

                    out.write(response);
                    out.write("\r\n");
                    out.flush();
                }
            } catch (IOException e) {
                logger.warn("IO exception with {} {}", socket.getRemoteSocketAddress(), e.getMessage());
            }
        });
    }

    public void close() {

        executorService.shutdown();

        try {
            boolean result = executorService.awaitTermination(5, TimeUnit.SECONDS);
            if (!result) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
