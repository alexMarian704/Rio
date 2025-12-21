package org.rio.commands;

import org.rio.store.KeyValueStore;
import org.rio.store.WrongTypeException;

import java.util.List;

import static org.rio.constants.ResponseConstants.*;

public class ListAddLastCommand extends AbstractCommand {

    private static final String NAME = "RPUSH";

    public ListAddLastCommand(KeyValueStore keyValueStore) {
        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        if (data.size() < 3) {
            return String.format(WRONG_NUMBER_OF_ARGUMENTS, NAME);
        }

        String key = data.get(1);
        List<String> values = data.subList(1, data.size());

        try {
            for (String value : values) {
                keyValueStore.addLast(key, value);
            }
        } catch (WrongTypeException e) {
            throw new RuntimeException(e);
        }

        return OK_MESSAGE;
    }
}
