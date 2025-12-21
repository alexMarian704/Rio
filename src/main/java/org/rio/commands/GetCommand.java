package org.rio.commands;

import org.rio.store.KeyValueStore;
import org.rio.store.WrongTypeException;

import java.util.List;

import static org.rio.constants.ResponseConstants.*;

public class GetCommand extends AbstractCommand {

    private static final String NAME = "GET";

    public GetCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        if (data.size() != 2) {
            return String.format(WRONG_NUMBER_OF_ARGUMENTS, NAME);
        }

        String value;
        try {
            value = keyValueStore.get(data.get(1));
        } catch (WrongTypeException e) {
            return WRONG_DATA_TYPE;
        }

        return value == null ? NULL_VALUE : value;
    }
}
