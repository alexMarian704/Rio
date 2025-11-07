package org.rio.processing;

import org.rio.commands.AbstractCommand;
import org.rio.commands.CommandRegistry;

public class CommandHandler {

    private final CommandRegistry commandRegistry;

    public CommandHandler(CommandRegistry commandRegistry) {

        this.commandRegistry = commandRegistry;
    }

    public String handle(String line) {

        int separator = line.indexOf(' ');
        String command = separator == -1 ? line : line.substring(0, separator);
        command = command.toUpperCase();

        AbstractCommand commandClass = commandRegistry.getCommand(command);
        if (commandClass == null) {
            return "-ERR unknown command";
        }

        try {
//            return switch (command) {
//                case "PING" -> "PONG";
//                case "QUIT" -> "__QUIT__";
//                case "SET" -> handleSet(line, separator);
//                case "GET" -> handleGet(line, separator);
//                case "DEL" -> handleDelete(line, separator);
//                case "EXISTS" -> handleExists(line, separator);
//                default -> "-ERR unknown command";
//            };
            return commandClass.handle(line.substring(separator + 1));
        } catch (Exception e) {
            return "-ERR internal server error";
        }
    }

//    private String handleSet(String line, int separator) {
//
//        if (separator == -1) {
//            return "-ERR wrong number of arguments for SET";
//        }
//
//        int nextSeparator = line.indexOf(' ', separator + 1);
//        if (nextSeparator == -1) {
//            return "-ERR wrong number of arguments for SET";
//        }
//
//        String key = line.substring(separator + 1, nextSeparator);
//        String value = line.substring(nextSeparator + 1);
//
//        if (key.isEmpty()) {
//            return "-ERR empty key";
//        }
//
//        keyValueStore.insert(key, value);
//
//        return "OK";
//    }
//
//    private String handleGet(String line, int separator) {
//
//        if (separator == -1) {
//            return "-ERR wrong number of arguments for GET";
//        }
//
//        String key = line.substring(separator + 1);
//        if (key.isEmpty()) {
//            return "-ERR the key is empty";
//        }
//
//        String value = keyValueStore.get(key);
//        return value == null ? "(nil)" : value;
//    }
//
//    private String handleDelete(String line, int separator) {
//
//        if (separator == -1) {
//            return "-ERR wrong number of arguments for DEL";
//        }
//
//        String key = line.substring(separator + 1);
//        if (key.isEmpty()) {
//            return "-ERR wrong number of arguments for DEL";
//        }
//
//        String value = keyValueStore.remove(key);
//
//        return ":" + value;
//    }
//
//    private String handleExists(String line, int separator) {
//
//        if (separator == -1) {
//            return "-ERR wrong number of arguments for EXISTS";
//        }
//
//        String key = line.substring(separator + 1);
//        if (key.isEmpty()) {
//            return "-ERR wrong number of arguments for EXISTS";
//        }
//
//        return ":" + keyValueStore.exists(key);
//    }
}
