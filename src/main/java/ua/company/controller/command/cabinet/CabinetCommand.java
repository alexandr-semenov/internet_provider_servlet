package ua.company.controller.command.cabinet;

import ua.company.constants.Page;
import ua.company.controller.command.Command;
import ua.company.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CabinetCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        request.setAttribute("user", user);

        return Page.CABINET_PAGE;
    }
}
