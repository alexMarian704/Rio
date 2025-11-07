package org.rio.commands;

import org.rio.server.KeyValueStore;

public class AppendCommand extends AbstractCommand {

    private static final String NAME = "APPEND";

    public AppendCommand(KeyValueStore keyValueStore) {

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

        if (key.isEmpty()) {
            return "-ERR empty key";
        }

        String current = keyValueStore.get(key);

        keyValueStore.insert(key, current == null ? value : current + value);

        return "OK";
    }
}
