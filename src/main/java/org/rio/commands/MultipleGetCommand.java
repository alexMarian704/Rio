package org.rio.commands;

import org.rio.server.KeyValueStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.rio.constants.ResponseConstants.NULL_VALUE;

public class MultipleGetCommand extends AbstractCommand {

    private static final String NAME = "MGET";

    public MultipleGetCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(String line) {

        String[] keys = line.split(" ");
        StringBuilder result = new StringBuilder();
        int index = 1;

        for (String key : keys) {
            if (key.isBlank()) {
                continue;
            }

            String value = keyValueStore.get(key);
            result.append(index++).append(")").append(" ").append(Objects.requireNonNullElse(value, NULL_VALUE)).append(' ');
        }

        return result.toString();
    }
}
