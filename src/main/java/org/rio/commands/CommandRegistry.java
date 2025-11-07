package org.rio.commands;

import java.util.HashMap;
import java.util.Map;

public final class CommandRegistry {

    private final Map<String, AbstractCommand> commands = new HashMap<>();

    public void register(AbstractCommand command) {

        if (command == null) {
            throw new IllegalArgumentException("Command cannot be null");
        }

        if (command.getName() == null) {
            throw new IllegalArgumentException("Command can't have a null name");
        }

        if (commands.containsKey(command.getName())) {
            throw new IllegalStateException("A command with name " + command.getName() + " is already registered");
        }

        commands.put(command.getName(), command);
    }

    public AbstractCommand getCommand(String command) {

        return commands.get(command);
    }
}
