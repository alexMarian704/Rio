package org.rio.commands;

import org.rio.server.KeyValueStore;

public class SetIfNotExistsCommand extends AbstractCommand {

    private final static String NAME = "SETX";

    public SetIfNotExistsCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(String line) {

        int separator = line.indexOf(' ');
        if (separator == -1) {
            return "-ERR wrong number of arguments fro SETX";
        }

        String key = line.substring(0, separator);

        if (!keyValueStore.exists(key)) {
            String value = line.substring(separator + 1);
            keyValueStore.insert(key, value);

            return ":1";
        }

        return ":0";
    }
}
