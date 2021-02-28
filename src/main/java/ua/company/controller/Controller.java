package ua.company.controller;

import ua.company.controller.command.Command;
import ua.company.controller.command.CommandContainer;
import ua.company.controller.command.PostCommandContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Controller extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandContainer.get(CommandContainer.getCommandName(request));
        String forward = command.execute(request, response);
        request.getRequestDispatcher(forward).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = PostCommandContainer.get(PostCommandContainer.getCommandName(request));
        String forward = command.execute(request, response);
        request.getRequestDispatcher(forward).forward(request, response);
    }
}
