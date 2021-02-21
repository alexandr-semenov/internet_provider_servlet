package ua.company.controller.command.cabinet;

import ua.company.constants.Page;
import ua.company.controller.command.Command;
import ua.company.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CabinetCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        request.setAttribute("user", user);

        return Page.CABINET_PAGE;
    }
}
