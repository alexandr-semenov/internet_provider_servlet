package ua.company.filter;

import org.apache.log4j.Logger;

import ua.company.constants.RoleName;
import ua.company.controller.command.AdminCommands;
import ua.company.controller.command.CommonCommands;
import ua.company.controller.command.GuestCommands;
import ua.company.controller.command.UserCommands;
import ua.company.exception.ApiException;
import ua.company.model.entity.Role;
import ua.company.model.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import java.util.*;

public class CommandAccessFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(CommandAccessFilter.class);
    private final Map<String, List<String>> accessMap = new HashMap<>();
    private List<String> guestCommands;
    private List<String> commonCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        accessMap.put(RoleName.ROLE_ADMIN, AdminCommands.getCommands());
        accessMap.put(RoleName.ROLE_USER, UserCommands.getCommands());
        guestCommands = GuestCommands.getCommands();
        commonCommands = CommonCommands.getCommands();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (accessAllowed(request)) {
            chain.doFilter(request, response);
        } else {
            throw new ApiException(Arrays.asList("forbidden_error"), HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean accessAllowed(ServletRequest request) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String commandName = httpServletRequest.getRequestURI();

        if (commandName == null || commandName.isEmpty()) {
            return false;
        }

        if (guestCommands.contains(commandName)) {
            return true;
        }

        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            return false;
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return false;
        }

        Role role = user.getRole();

        return accessMap.get(role.getName()).contains(commandName) || commonCommands.contains(commandName);
    }
}
