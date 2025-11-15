package org.rio.commands;

import org.rio.server.KeyValueStore;

import java.util.HashMap;
import java.util.Map;

import static org.rio.constants.ResponseConstants.INVALID_VALUES;
import static org.rio.constants.ResponseConstants.OK_MESSAGE;

public class MultipleSetCommand extends AbstractCommand {

    private static final String NAME = "MSET";

    public MultipleSetCommand(KeyValueStore keyValueStore) {

        super(keyValueStore, NAME);
    }

    @Override
    public String handle(String line) {

        Map<String, String> store = new HashMap<>();
        int n = line.length();
        int index = 0;

        while (index < n) {

            while (index < n && Character.isWhitespace(line.charAt(index))) index++;

            if (index >= n) {
                break;
            }

            int keyStart = index;
            while (index < n && !Character.isWhitespace(line.charAt(index))) index++;
            if (keyStart == index) {
                return INVALID_VALUES;
            }
            String key = line.substring(keyStart, index);

            while (index < n && Character.isWhitespace(line.charAt(index))) index++;
            if (index >= n || line.charAt(index) != '"') {
                return INVALID_VALUES;
            }

            index++;
            StringBuilder value = new StringBuilder();
            boolean closed = false;
            while (index < n) {
                char c = line.charAt(index++);
                if (c == '\\') {
                    if (index >= n) {
                        return INVALID_VALUES;
                    }

                    char esc = line.charAt(index++);
                    switch (esc) {
                        case '"':
                            value.append('"');
                            break;
                        case '\\':
                            value.append('\\');
                            break;
                        case 'n':
                            value.append('\n');
                            break;
                        case 'r':
                            value.append('\r');
                            break;
                        case 't':
                            value.append('\t');
                        default:
                            value.append(esc);
                            break;
                    }
                } else if (c == '"') {
                    closed = true;
                    break;
                } else {
                    value.append(c);
                }
            }

            if (!closed) {
                return INVALID_VALUES;
            }

            store.put(key, value.toString());
        }

        keyValueStore.insertAll(store);

        return OK_MESSAGE;
    }
}
