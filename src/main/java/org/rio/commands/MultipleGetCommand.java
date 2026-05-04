package org.rio.commands;

import org.rio.storage.KeyValueStore;
import org.rio.storage.exceptions.WrongTypeException;

import java.util.List;
import java.util.Objects;

import static org.rio.constants.ResponseConstants.*;

public class MultipleGetCommand extends AbstractCommand {

    private static final String NAME = "MGET";

    public MultipleGetCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        if (data.size() < 2) {
            return String.format(WRONG_NUMBER_OF_ARGUMENTS, NAME);
        }

        List<String> keys = data.subList(1, data.size());
        StringBuilder result = new StringBuilder();
        int index = 1;

        for (String key : keys) {
            String value;

            try {
                value = keyValueStore.get(key);
            } catch (WrongTypeException e) {
                return WRONG_DATA_TYPE + " for " + key;
            }
            result.append(index++).append(")").append(" ").append(Objects.requireNonNullElse(value, NULL_VALUE)).append(' ');
        }

        return result.toString();
    }
}
