package org.rio.commands;

import org.rio.store.KeyValueStore;

import java.util.List;

public class PingCommand extends AbstractCommand {

    private static final String NAME = "PING";

    public PingCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        return "PONG";
    }
}
