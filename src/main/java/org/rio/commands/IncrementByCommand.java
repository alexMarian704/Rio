package org.rio.commands;

import org.rio.server.KeyValueStore;

public class IncrementByCommand extends AbstractCommand {

    private static final String NAME = "INCBY";

    public IncrementByCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(String line) {

        int separator = line.indexOf(' ');
        if (separator == -1) {
            return "-ERR wrong number of arguments for INCBY";
        }

        String key = line.substring(0, separator);
        String value = line.substring(separator + 1);

        long delta;
        try {
            delta = Long.parseLong(value);
        } catch (NumberFormatException e) {
            return "-ERR the value is not a valid number";
        }

        String currentStr = keyValueStore.get(key);
        long current = 0L;
        if (currentStr != null) {
            try {
                current = Long.parseLong(currentStr);
            } catch (NumberFormatException e) {
                return "-ERR current value is not an integer";
            }
        }

        long next;
        try {
            next = Math.addExact(delta, current);
        } catch (ArithmeticException e) {
            return "-ERR the result will overflow";
        }
        keyValueStore.insert(key, Long.toString(next));

        return ":" + next;
    }
}
