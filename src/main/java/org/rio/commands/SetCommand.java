package org.rio.commands;

import org.rio.storage.KeyValueStore;
import org.rio.storage.exceptions.WrongTypeException;

import java.util.List;

import static org.rio.constants.ResponseConstants.WRONG_DATA_TYPE;
import static org.rio.constants.ResponseConstants.WRONG_NUMBER_OF_ARGUMENTS;

public class SetCommand extends AbstractCommand {

    private static final String NAME = "SET";

    public SetCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        if (data.size() != 3) {
            return String.format(WRONG_NUMBER_OF_ARGUMENTS, NAME);
        }

        String key = data.get(1);
        String value = data.get(2);

        try {
            keyValueStore.insert(key, value);
        } catch (WrongTypeException e) {
            return WRONG_DATA_TYPE;
        }

        return "OK";
    }
}
