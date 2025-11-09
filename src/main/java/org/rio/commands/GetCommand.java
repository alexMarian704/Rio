package org.rio.commands;

import org.rio.server.KeyValueStore;

import static org.rio.constants.ResponseConstants.NULL_VALUE;

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

        return value == null ? NULL_VALUE : value;
    }
}
