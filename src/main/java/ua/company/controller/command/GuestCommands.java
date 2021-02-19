package ua.company.controller.command;

import java.util.Arrays;
import java.util.List;

public class GuestCommands {
    private final static List<String> commands;

    static {
        commands = Arrays.asList("/", "/login", "/api/login");
    }

    public static List<String> getCommands() {
        return commands;
    }
}
