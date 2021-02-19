package ua.company.controller.command;

import ua.company.controller.command.admin.AdminActivateUserCommand;
import ua.company.controller.command.admin.AdminCommand;
import ua.company.controller.command.admin.AdminPendingUsersCommand;
import ua.company.controller.command.cabinet.CabinetCommand;
import ua.company.model.service.UserService;
import ua.company.util.ValidationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("/login", new LoginCommand());
        commands.put("/api/login", new ApiLoginCommand(new UserService(), new ValidationService()));

        commands.put("/logout", new LogoutCommand());

        commands.put("/api-response", new ApiResponseCommand());

        commands.put("/cabinet", new CabinetCommand());

        commands.put("/admin", new AdminCommand());
        commands.put("/admin/pending-users", new AdminPendingUsersCommand(new UserService()));
        commands.put("/admin/activate-user", new AdminActivateUserCommand(new UserService(), new ValidationService()));
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            throw new RuntimeException("Command not found");
        }

        return commands.get(commandName);
    }

    public static String getCommandName(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
