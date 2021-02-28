package ua.company.model.dao;

import ua.company.exception.ApiException;

public abstract class DaoFactory {
    public abstract UserDaoImpl createUserDao() throws ApiException;
    public abstract RoleDaoImpl createRoleDao() throws ApiException;
    public abstract ProductDaoImpl createProductDao() throws ApiException;
    public abstract TariffDaoImpl createTariffDao() throws ApiException;
    public abstract TariffOptionDaoImpl createTariffOptionDao() throws ApiException;
    public abstract SubscriptionDaoImpl createSubscriptionDao() throws ApiException;
    public abstract AccountDaoImpl createAccountDao() throws ApiException;
    public static DaoFactory getInstance() {
        return new JdbcDaoFactory();
    }
}
