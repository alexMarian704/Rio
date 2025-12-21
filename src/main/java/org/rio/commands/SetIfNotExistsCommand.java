package org.rio.commands;

import org.rio.store.KeyValueStore;
import org.rio.store.WrongTypeException;

import java.util.List;

import static org.rio.constants.ResponseConstants.WRONG_DATA_TYPE;
import static org.rio.constants.ResponseConstants.WRONG_NUMBER_OF_ARGUMENTS;

public class SetIfNotExistsCommand extends AbstractCommand {

    private final static String NAME = "SETX";

    public SetIfNotExistsCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        if (data.size() != 3) {
            return String.format(WRONG_NUMBER_OF_ARGUMENTS, NAME);
        }

        String key = data.get(1);

        if (!keyValueStore.exists(key)) {
            String value = data.get(2);
            try {
                keyValueStore.insert(key, value);
            } catch (WrongTypeException e) {
                return WRONG_DATA_TYPE;
            }

            return ":1";
        }

        return ":0";
    }
}
