package ua.company.controller.command;

import ua.company.constants.Path;

import java.util.Arrays;
import java.util.List;

public class AdminCommands {
    private final static List<String> commands;

    static {
        commands = Arrays.asList(
                Path.COMMAND_ADMIN, Path.COMMAND_ADMIN_PENDING_USERS, Path.COMMAND_ADMIN_ACTIVATE_USER,
                Path.COMMAND_ADMIN_TARIFF_CREATE, Path.COMMAND_PRODUCT, Path.COMMAND_ADMIN_EDIT_TARIFF,
                Path.COMMAND_ADMIN_EDIT_TARIFF_OPTION, Path.COMMAND_ADMIN_CREATE_TARIFF_OPTION,
                Path.COMMAND_ADMIN_DELETE_TARIFF_OPTION, Path.COMMAND_ADMIN_EDIT_TARIFFS
        );
    }

    public static List<String> getCommands() {
        return commands;
    }
}
