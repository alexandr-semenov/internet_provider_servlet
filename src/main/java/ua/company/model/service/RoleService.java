package ua.company.model.service;

import ua.company.exception.ApiException;
import ua.company.model.dao.DaoFactory;
import ua.company.model.dao.RoleDaoImpl;
import ua.company.model.entity.Role;

import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

public class RoleService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public Role getRoleById(int id) throws ApiException {
        RoleDaoImpl roleDao = daoFactory.createRoleDao();
        Role role = roleDao.findById(id);

        if (role == null) {
            throw new ApiException(Arrays.asList("role_not_found_error"), HttpServletResponse.SC_NOT_FOUND);
        }

        return role;
    }
}
