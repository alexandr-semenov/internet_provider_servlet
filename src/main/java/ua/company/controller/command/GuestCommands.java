package ua.company.controller.command;

import ua.company.constants.Path;

import java.util.Arrays;
import java.util.List;

public class GuestCommands {
    private final static List<String> commands;

    static {
        commands = Arrays.asList(
                Path.COMMAND_INDEX, Path.COMMAND_LOGIN, Path.COMMAND_API_LOGIN, Path.COMMAND_MAIN_INTERNET,
                Path.COMMAND_MAIN_INTERNET_TV, Path.COMMAND_MAIN_TELEVISION, Path.COMMAND_MAIN_TELEPHONY,
                Path.COMMAND_SUBSCRIPTION, Path.COMMAND_SUBSCRIBE
        );
    }

    public static List<String> getCommands() {
        return commands;
    }
}
