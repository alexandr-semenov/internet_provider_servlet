package ua.company.model.dao;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDaoImpl createUserDao();

    public abstract RoleDaoImpl createRoleDao();

    public static DaoFactory getInstance() {
        return new JdbcDaoFactory();
    }
}
