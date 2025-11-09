package org.rio.server;

import org.rio.commands.*;
import org.rio.processing.CommandHandler;
import org.rio.processing.Processor;
import org.rio.processing.ShardsProcessor;
import org.rio.processing.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final int port;
    private final int poolSize;
    private ServerSocket serverSocket;
    private volatile boolean running = false;
    private Coordinator coordinator;
    private final Processor processor;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public Server(int port, int poolSize) {

        this.port = port;
        this.poolSize = poolSize;

        KeyValueStore keyValueStore = new KeyValueStore();
        CommandRegistry registry = new CommandRegistry();
        registry.register(new QuitCommand(keyValueStore));
        registry.register(new PingCommand(keyValueStore));
        registry.register(new DelCommand(keyValueStore));
        registry.register(new GetCommand(keyValueStore));
        registry.register(new SetCommand(keyValueStore));
        registry.register(new ExistsCommand(keyValueStore));
        registry.register(new AppendCommand(keyValueStore));
        registry.register(new IncrementByCommand(keyValueStore));
        registry.register(new GetSetCommand(keyValueStore));
        registry.register(new SetIfNotExistsCommand(keyValueStore));

        CommandHandler commandHandler = new CommandHandler(registry);
        Validator validator = new Validator();
        this.processor = new ShardsProcessor(poolSize, commandHandler, validator);
    }

    public void start() throws IOException {

        serverSocket = new ServerSocket(port);
        running = true;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                close();
            } catch (Exception e) {
                // nothing to do
            }
        }));

        coordinator = new Coordinator(processor);
        processor.start();

        logger.info("Server started");

        while (running) {
            try {
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(120_000);
                coordinator.coordinateConnection(socket);
            } catch (IOException e) {
                if (running) {
                    logger.warn("Error while running {}", e.getMessage());
                }
            }
        }
    }

    public void close() throws IOException {

        logger.info("Server stopped");

        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
        if (coordinator != null) {
            coordinator.close();
        }
        processor.stop();
    }
}
