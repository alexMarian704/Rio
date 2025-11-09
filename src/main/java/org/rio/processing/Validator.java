package org.rio.processing;

import static org.rio.constants.MemoryConstants.MAX_LINE_SIZE;

public class Validator {

    public void validate(String line) {

        if (line == null) {
            throw new IllegalArgumentException("-ERR line is null");
        }

        if (line.isEmpty()) {
            throw new IllegalArgumentException("-ERR line empty");
        }

        if (line.length() > MAX_LINE_SIZE) {
            throw new IllegalArgumentException("-ERR line too long");
        }
    }
}
