package ua.company.model.service;

import ua.company.constants.SubscriptionStatus;
import ua.company.exception.ApiException;
import ua.company.model.dao.AccountDaoImpl;
import ua.company.model.dao.DaoFactory;
import ua.company.model.entity.Account;
import ua.company.model.entity.Subscription;
import ua.company.model.entity.User;

import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

public class AccountService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    public User depositAccount(User user, Double amount) throws ApiException {
        AccountDaoImpl accountDao = daoFactory.createAccountDao();

        Account account = user.getAccount();
        Double currentAmount = account.getAmount();
        Double newAmount = currentAmount + amount;

        try {
            accountDao.makeDeposit(account, newAmount);
            user.getAccount().setAmount(newAmount);
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return user;
    }

    public User debitAccount(User user, Double amount) throws ApiException {
        AccountDaoImpl accountDao = daoFactory.createAccountDao();

        Account account = user.getAccount();
        Double currentAmount = account.getAmount();

        if (currentAmount <= amount) {
            return user;
        }

        Double newAmount = currentAmount - amount;
        try {
            accountDao.makeDebit(account, newAmount, user.getSubscription());
            user.getAccount().setAmount(newAmount);
            user.getSubscription().setStatus(Subscription.Status.valueOf(SubscriptionStatus.ACTIVE));
        } catch (Exception e) {
            throw new ApiException(Arrays.asList(e.getMessage()), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return user;
    }
}
