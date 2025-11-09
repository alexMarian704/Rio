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
            return commandClass.handle(line.substring(separator + 1));
        } catch (Exception e) {
            return "-ERR internal server error";
        }
    }
}
