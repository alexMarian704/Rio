package org.rio.commands;

import org.rio.store.KeyValueStore;

import java.util.List;

import static org.rio.constants.ResponseConstants.NULL_VALUE;
import static org.rio.constants.ResponseConstants.WRONG_NUMBER_OF_ARGUMENTS;

public class DelCommand extends AbstractCommand {

    private static final String NAME = "DEL";

    public DelCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        if (data.size() != 2) {
            return String.format(WRONG_NUMBER_OF_ARGUMENTS, NAME);
        }

        Object value = keyValueStore.remove(data.get(1));

        return value != null ? value.toString() : NULL_VALUE;
    }
}
