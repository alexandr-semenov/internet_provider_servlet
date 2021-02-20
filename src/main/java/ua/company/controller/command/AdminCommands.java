package ua.company.controller.command;

import ua.company.constants.Path;

import java.util.Arrays;
import java.util.List;

public class AdminCommands {
    private final static List<String> commands;

    static {
        commands = Arrays.asList(
                Path.COMMAND_ADMIN, Path.COMMAND_ADMIN_PENDING_USERS, Path.COMMAND_ADMIN_ACTIVATE_USER
        );
    }

    public static List<String> getCommands() {
        return commands;
    }
}
