package org.rio.commands;

import org.rio.store.KeyValueStore;

import java.util.List;

import static org.rio.constants.ResponseConstants.WRONG_NUMBER_OF_ARGUMENTS;

public class ExistsCommand extends AbstractCommand {

    private static final String NAME = "EXISTS";

    public ExistsCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        if (data.size() != 2) {
            return String.format(WRONG_NUMBER_OF_ARGUMENTS, NAME);
        }

        boolean value = keyValueStore.exists(data.get(1));

        return ":" + (value ? 1 : 0);
    }
}
