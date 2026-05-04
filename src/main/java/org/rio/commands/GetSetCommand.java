package org.rio.commands;

import org.rio.storage.KeyValueStore;
import org.rio.storage.exceptions.WrongTypeException;

import java.util.List;

import static org.rio.constants.ResponseConstants.*;

public class GetSetCommand extends AbstractCommand {

    private final static String NAME = "GETSET";

    public GetSetCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        if (data.size() != 3) {
            return String.format(WRONG_NUMBER_OF_ARGUMENTS, NAME);
        }

        String key = data.get(1);
        String value = data.get(2);

        if (value.isBlank()) {
            return "-ERR the value cannot be empty";
        }

        String oldValue;
        try {
            oldValue = keyValueStore.get(key);

            keyValueStore.insert(key, value);
        } catch (WrongTypeException e) {
            return WRONG_DATA_TYPE;
        }

        return oldValue != null ? oldValue : NULL_VALUE;
    }
}
