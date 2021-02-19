package ua.company.controller.command;

import java.util.Arrays;
import java.util.List;

public class UserCommands {
    private final static List<String> commands;

    static {
        commands = Arrays.asList("/cabinet", "/cabinet/deposit");
    }

    public static List<String> getCommands() {
        return commands;
    }
}
