package ua.company.controller.command.cabinet.deposit;

import ua.company.constants.Page;
import ua.company.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DepositCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return Page.CABINET_DEPOSIT_PAGE;
    }
}
