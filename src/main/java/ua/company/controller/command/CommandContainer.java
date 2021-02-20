package ua.company.controller.command;

import ua.company.constants.Path;
import ua.company.controller.command.admin.AdminActivateUserCommand;
import ua.company.controller.command.admin.AdminCommand;
import ua.company.controller.command.admin.AdminPendingUsersCommand;
import ua.company.controller.command.cabinet.CabinetCommand;
import ua.company.controller.command.main.InternetMainCommand;
import ua.company.controller.command.main.InternetTvCommand;
import ua.company.controller.command.main.TelephonyCommand;
import ua.company.controller.command.main.TelevisionCommand;
import ua.company.controller.command.subscription.SubscribeCommand;
import ua.company.controller.command.subscription.SubscriptionCommand;
import ua.company.model.service.ProductService;
import ua.company.model.service.SubscriptionService;
import ua.company.model.service.TariffService;
import ua.company.model.service.UserService;
import ua.company.util.ValidationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put(Path.COMMAND_LOGIN, new LoginCommand());
        commands.put(Path.COMMAND_API_LOGIN, new ApiLoginCommand(new UserService(), new ValidationService()));
        commands.put(Path.COMMAND_LOGUT, new LogoutCommand());

        commands.put(Path.COMMAND_API_RESPONSE, new ApiResponseCommand());

        commands.put(Path.COMMAND_CABINET, new CabinetCommand());

        commands.put(Path.COMMAND_ADMIN, new AdminCommand());
        commands.put(Path.COMMAND_ADMIN_PENDING_USERS, new AdminPendingUsersCommand(new UserService()));
        commands.put(Path.COMMAND_ADMIN_ACTIVATE_USER, new AdminActivateUserCommand(new UserService(), new ValidationService()));

        commands.put(Path.COMMAND_MAIN_INTERNET, new InternetMainCommand(new ProductService()));
        commands.put(Path.COMMAND_MAIN_INTERNET_TV, new InternetTvCommand(new ProductService()));
        commands.put(Path.COMMAND_MAIN_TELEVISION, new TelevisionCommand(new ProductService()));
        commands.put(Path.COMMAND_MAIN_TELEPHONY, new TelephonyCommand(new ProductService()));

        commands.put(Path.COMMAND_SUBSCRIPTION, new SubscriptionCommand(new TariffService()));
        commands.put(Path.COMMAND_SUBSCRIBE, new SubscribeCommand(new SubscriptionService(), new ValidationService()));
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
