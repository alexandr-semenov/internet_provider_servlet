package ua.company.controller.command;

import java.util.Collections;
import java.util.List;

public class CommonCommands {
    private final static List<String> commands;

    static {
        commands = Collections.singletonList("/logout");
    }

    public static List<String> getCommands() {
        return commands;
    }
}
