package ua.company.controller.command.login;

import ua.company.constants.Page;
import ua.company.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return Page.LOGIN_PAGE;
    }
}
