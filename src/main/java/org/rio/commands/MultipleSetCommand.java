package org.rio.commands;

import org.rio.store.KeyValueStore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.rio.constants.ResponseConstants.INVALID_VALUES;
import static org.rio.constants.ResponseConstants.OK_MESSAGE;

public class MultipleSetCommand extends AbstractCommand {

    private static final String NAME = "MSET";

    public MultipleSetCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        Map<String, String> store = new HashMap<>();

        keyValueStore.insertAll(store);

        return OK_MESSAGE;
    }
}
