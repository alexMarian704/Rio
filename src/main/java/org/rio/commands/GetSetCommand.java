package org.rio.commands;

import org.rio.server.KeyValueStore;

import static org.rio.constants.ResponseConstants.NULL_VALUE;

public class GetSetCommand extends AbstractCommand {

    private final static String NAME = "GETSET";

    public GetSetCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(String line) {

        int separator = line.indexOf(' ');
        if (separator == -1) {
            return "-ERR wrong number of arguments for GETSET";
        }

        String key = line.substring(0, separator);
        String value = line.substring(separator + 1);

        if (value.isBlank()) {
            return "-ERR the value cannot be empty";
        }

        String oldValue = keyValueStore.get(key);
        keyValueStore.insert(key, value);

        return oldValue != null ? oldValue : NULL_VALUE;
    }
}
