package org.rio.commands;

import org.rio.server.KeyValueStore;

public class QuitCommand extends AbstractCommand {

    private static final String NAME = "QUIT";

    public QuitCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(String line) {

        return "__QUIT__";
    }
}
