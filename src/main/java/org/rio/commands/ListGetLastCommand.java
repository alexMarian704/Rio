package org.rio.commands;

import org.rio.store.KeyValueStore;
import org.rio.store.WrongTypeException;

import java.util.List;

import static org.rio.constants.ResponseConstants.*;

public class ListGetLastCommand extends AbstractCommand {

    private static final String NAME = "GETLAST";

    public ListGetLastCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        if (data.size() != 2) {
            return String.format(WRONG_NUMBER_OF_ARGUMENTS, NAME);
        }

        String key = data.get(1);
        String value;

        try {
            value = keyValueStore.getLast(key);
        } catch (WrongTypeException e) {
            return WRONG_DATA_TYPE;
        }

        return value == null ? NULL_VALUE : value;
    }
}
