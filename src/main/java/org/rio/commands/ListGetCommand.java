package org.rio.commands;

import org.rio.store.KeyValueStore;
import org.rio.store.WrongTypeException;

import java.util.Deque;
import java.util.List;
import java.util.Objects;

import static org.rio.constants.ResponseConstants.*;

public class ListGetCommand extends AbstractCommand {

    private static final String NAME = "LSTGET";

    public ListGetCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        if (data.size() != 2) {
            return String.format(WRONG_NUMBER_OF_ARGUMENTS, NAME);
        }

        String key = data.get(1);
        Deque<String> values;

        try {
            values = keyValueStore.getFullList(key);
        } catch (WrongTypeException e) {
            return WRONG_DATA_TYPE;
        }

        if (values == null) {
            return NULL_VALUE;
        }

        StringBuilder result = new StringBuilder();

        int index = 1;
        for (String value : values) {
            result.append(index++).append(")").append(" ").append(Objects.requireNonNullElse(value, NULL_VALUE)).append(' ');
        }

        return result.toString();
    }
}
