package ua.company.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serializable;

public abstract class Command implements Serializable {
    public abstract String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
