package org.rio.processing;

import java.util.List;

public class Validator {

    public void validate(List<String> data) {

        if (data == null) {
            throw new IllegalArgumentException("-ERR content is null");
        }

        if (data.isEmpty()) {
            throw new IllegalArgumentException("-ERR there is no data");
        }
    }
}
