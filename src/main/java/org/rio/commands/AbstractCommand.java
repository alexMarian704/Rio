package org.rio.commands;

import org.rio.store.KeyValueStore;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand {

    protected final KeyValueStore keyValueStore;
    private final String command;

    public AbstractCommand(KeyValueStore keyValueStore, String command) {

        if (keyValueStore == null) {
            throw new IllegalArgumentException("The store cannot be null");
        }
        if (command == null) {
            throw new IllegalArgumentException("The command cannot be null");
        }

        this.keyValueStore = keyValueStore;
        this.command = command;
    }

    public String getName() {

        return command;
    }

    public abstract String handle(List<String> data);

    public List<String> extractValues(String line) {

        List<String> result = new ArrayList<>();

        int n = line.length();
        int index = 0;

        while (index < n) {

            while (index < n && Character.isWhitespace(line.charAt(index))) index++;
            if (index >= n) {
                break;
            }

            if (line.charAt(index) != '"') {
                throw new IllegalArgumentException("Expected '\"' to start a value");
            }
            index++;

            StringBuilder value = new StringBuilder();
            boolean closed = false;
            while (index < n) {
                char c = line.charAt(index++);
                if (c == '\\') {
                    if (index >= n) {
                        throw new IllegalArgumentException("Invalid values");
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
                throw new IllegalArgumentException("Invalid values");
            }

            result.add(value.toString());
        }

        return result;
    }
}
