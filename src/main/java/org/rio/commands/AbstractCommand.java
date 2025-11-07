package org.rio.commands;

import org.rio.server.KeyValueStore;

public abstract class AbstractCommand {

    protected final KeyValueStore keyValueStore;
    private final String command;

    public AbstractCommand(KeyValueStore keyValueStore, String command) {

        if (keyValueStore == null) {
            throw new IllegalArgumentException("The store cannot be null");
        }
        if (command == null) {
            throw new IllegalArgumentException("The command cannot be null");
        }

        this.keyValueStore = keyValueStore;
        this.command = command;
    }

    public String getName() {

        return command;
    }

    public abstract String handle(String line);
}
