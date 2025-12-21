package org.rio.commands;

import org.rio.store.KeyValueStore;
import org.rio.store.WrongTypeException;

import java.util.List;

import static org.rio.constants.ResponseConstants.WRONG_DATA_TYPE;
import static org.rio.constants.ResponseConstants.WRONG_NUMBER_OF_ARGUMENTS;

public class IncrementByCommand extends AbstractCommand {

    private static final String NAME = "INCBY";

    public IncrementByCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(List<String> data) {

        if (data.size() != 3) {
            return String.format(WRONG_NUMBER_OF_ARGUMENTS, NAME);
        }

        String key = data.get(1);
        String value = data.get(2);

        long delta;
        try {
            delta = Long.parseLong(value);
        } catch (NumberFormatException e) {
            return "-ERR the value is not a valid number";
        }

        String currentStr;
        try {
            currentStr = keyValueStore.get(key);
        } catch (WrongTypeException e) {
            return WRONG_DATA_TYPE;
        }
        long current = 0L;
        if (currentStr != null) {
            try {
                current = Long.parseLong(currentStr);
            } catch (NumberFormatException e) {
                return "-ERR current value is not an integer";
            }
        }

        long next;
        try {
            next = Math.addExact(delta, current);
        } catch (ArithmeticException e) {
            return "-ERR the result will overflow";
        }


        try {
            keyValueStore.insert(key, Long.toString(next));
        } catch (WrongTypeException e) {
            return WRONG_DATA_TYPE;
        }

        return ":" + next;
    }
}
