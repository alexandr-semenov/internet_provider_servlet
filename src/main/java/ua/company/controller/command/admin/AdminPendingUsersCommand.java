package ua.company.controller.command.admin;

import ua.company.constants.Page;
import ua.company.controller.command.Command;
import ua.company.model.dto.user.UserDto;
import ua.company.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdminPendingUsersCommand extends Command {
    private final UserService userService;

    public AdminPendingUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
        int size = Integer.parseInt(request.getParameter("size") != null ? request.getParameter("size") : "5");
        int offset = (page - 1) * size;
        int totalPages;

        List<UserDto> users = userService.getNotActiveUsersPaginated(size, offset);
        request.setAttribute("users", users);

        int totalUsers = userService.getTotalNotActiveUsers();
        if (totalUsers % size == 0) {
            totalPages = totalUsers / size;
        } else {
            totalPages = totalUsers / size + 1;
        }

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            request.setAttribute("pageNumbers", pageNumbers);
        }
        request.setAttribute("size", size);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        return Page.ADMIN_PENDING_USERS_PAGE;
    }
}
