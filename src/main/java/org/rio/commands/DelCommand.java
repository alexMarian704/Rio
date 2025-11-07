package org.rio.commands;

import org.rio.server.KeyValueStore;

public class DelCommand extends AbstractCommand {

    private static final String NAME = "DEL";

    public DelCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(String line) {

        if (line.isEmpty()) {
            return "-ERR wrong number of arguments for DEL";
        }

        String value = keyValueStore.remove(line);

        return ":" + value;
    }
}
