package org.rio.processing;

import org.rio.commands.AbstractCommand;
import org.rio.commands.CommandRegistry;

import java.util.List;

import static org.rio.constants.ResponseConstants.INTERNAL_SERVER_ERROR;
import static org.rio.constants.ResponseConstants.UNKNOWN_COMMAND;

public class CommandHandler {

    private final CommandRegistry commandRegistry;

    public CommandHandler(CommandRegistry commandRegistry) {

        this.commandRegistry = commandRegistry;
    }

    public String handle(List<String> data) {

        String command = data.getFirst();
        command = command.toUpperCase();

        AbstractCommand commandClass = commandRegistry.getCommand(command);
        if (commandClass == null) {
            return UNKNOWN_COMMAND;
        }

        try {
            return commandClass.handle(data);
        } catch (Exception e) {
            return INTERNAL_SERVER_ERROR;
        }
    }
}
