package ua.company.controller.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {
    private static final Logger LOGGER = Logger.getLogger(CommandContainer.class);
    private static final Map<String, Command> commands = new TreeMap<>();

    static {
        commands.put("/login", new LoginCommand());
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOGGER.error("Command: " + commandName +  " not found");

            throw new RuntimeException("Command not found");
        }

        return commands.get(commandName);
    }

    public static String getCommandName(HttpServletRequest request) {
        return request.getRequestURI();
    }
}
