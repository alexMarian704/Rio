package org.rio.commands;

import org.rio.server.KeyValueStore;

public class SetCommand extends AbstractCommand {

    private static final String NAME = "SET";

    public SetCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(String line) {

        int nextSeparator = line.indexOf(' ');
        if (nextSeparator == -1) {
            return "-ERR wrong number of arguments for SET";
        }

        String key = line.substring(0, nextSeparator);
        String value = line.substring(nextSeparator + 1);

        keyValueStore.insert(key, value);

        return "OK";
    }
}
