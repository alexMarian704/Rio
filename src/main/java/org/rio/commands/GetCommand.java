package org.rio.commands;

import org.rio.server.KeyValueStore;

public class GetCommand extends AbstractCommand {

    private static final String NAME = "GET";

    public GetCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(String line) {

        if (line.isEmpty()) {
            return "-ERR wrong number of arguments for GET";
        }

        String value = keyValueStore.get(line);

        return value == null ? "(nil)" : value;
    }
}
