package org.rio.commands;

import org.rio.server.KeyValueStore;

public class PingCommand extends AbstractCommand {

    private static final String NAME = "PING";

    public PingCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(String line) {

        return "PONG";
    }
}
