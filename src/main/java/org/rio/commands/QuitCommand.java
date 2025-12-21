package org.rio.commands;

import org.rio.store.KeyValueStore;

import java.util.List;

public class QuitCommand extends AbstractCommand {

    private static final String NAME = "QUIT";

    public QuitCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        return "__QUIT__";
    }
}
