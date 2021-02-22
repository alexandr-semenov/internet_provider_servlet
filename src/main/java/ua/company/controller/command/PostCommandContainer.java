package ua.company.controller.command;

import ua.company.constants.Path;
import ua.company.controller.command.admin.AdminActivateUserCommand;
import ua.company.controller.command.admin.post.AdminTariffCommand;
import ua.company.controller.command.cabinet.deposit.post.DepositCommand;
import ua.company.controller.command.login.post.LoginCommand;
import ua.company.controller.command.subscription.SubscribeCommand;
import ua.company.model.service.AccountService;
import ua.company.model.service.SubscriptionService;
import ua.company.model.service.UserService;
import ua.company.util.ValidationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;

public class PostCommandContainer {
    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put(Path.COMMAND_LOGIN, new LoginCommand(new UserService(), new ValidationService()));

        commands.put(Path.COMMAND_ADMIN_ACTIVATE_USER, new AdminActivateUserCommand(new UserService(), new ValidationService()));
        commands.put(Path.COMMAND_ADMIN_TARIFF, new AdminTariffCommand());

        commands.put(Path.COMMAND_SUBSCRIBE, new SubscribeCommand(new SubscriptionService(new UserService()), new ValidationService()));

        commands.put(Path.COMMAND_CABINET_DEPOSIT, new DepositCommand(new AccountService(), new ValidationService()));

        commands.put(Path.COMMAND_API_RESPONSE, new ApiResponseCommand());
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
