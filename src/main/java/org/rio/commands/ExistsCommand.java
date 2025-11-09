package org.rio.commands;

import org.rio.server.KeyValueStore;

public class ExistsCommand extends AbstractCommand {

    private static final String NAME = "EXISTS";

    public ExistsCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(String line) {

        if (line.isEmpty()) {
            return "-ERR wrong number of arguments for DEL";
        }

        boolean value = keyValueStore.exists(line);

        return ":" + (value ? 1 : 0);
    }
}
