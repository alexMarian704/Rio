package org.rio.commands;

import org.rio.store.KeyValueStore;
import org.rio.store.WrongTypeException;

import java.util.List;

import static org.rio.constants.ResponseConstants.WRONG_DATA_TYPE;
import static org.rio.constants.ResponseConstants.WRONG_NUMBER_OF_ARGUMENTS;

public class AppendCommand extends AbstractCommand {

    private static final String NAME = "APPEND";

    public AppendCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        if (data.size() != 3) {
            return String.format(WRONG_NUMBER_OF_ARGUMENTS, NAME);
        }

        String key = data.get(1);
        String value = data.get(2);

        if (key.isEmpty()) {
            return "-ERR empty key";
        }

        String current;
        try {
            current = keyValueStore.get(key);

            keyValueStore.insert(key, current == null ? value : current + value);
        } catch (WrongTypeException e) {
            return WRONG_DATA_TYPE;
        }

        return "OK";
    }
}
