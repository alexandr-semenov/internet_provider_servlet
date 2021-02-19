package ua.company.controller.command;

import java.util.Arrays;
import java.util.List;

public class AdminCommands {
    private final static List<String> commands;

    static {
        commands = Arrays.asList("/admin", "/admin/pending-users", "/admin/activate-user");
    }

    public static List<String> getCommands() {
        return commands;
    }
}
