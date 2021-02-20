package ua.company.controller.command;

import ua.company.constants.Path;

import java.util.Arrays;
import java.util.List;

public class UserCommands {
    private final static List<String> commands;

    static {
        commands = Arrays.asList(
                Path.COMMAND_CABINET, "/cabinet/deposit"
        );
    }

    public static List<String> getCommands() {
        return commands;
    }
}
