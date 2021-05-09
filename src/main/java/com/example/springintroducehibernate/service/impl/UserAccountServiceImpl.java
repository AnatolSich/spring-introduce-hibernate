package com.example.springintroducehibernate.service.impl;

import com.example.springintroducehibernate.dao.impl.UserAccountDAO;
import com.example.springintroducehibernate.model.impl.UserAccount;
import com.example.springintroducehibernate.service.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private Logger log = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    @Autowired
    private UserAccountDAO userAccountDAO;

    @Override
    public void openAccount(long userID) {
        userAccountDAO.save(new UserAccount(userID, 0));
    }

    @Override
    public void refillAccount(long userID, double money) {
        double currentScore = userAccountDAO.findOne(userID).getScore();
        userAccountDAO.update(new UserAccount(userID, currentScore + money));
        log.info("Refill account-" + userID + " for " + money + "$");
    }

    @Override
    public void withdrawAccount(long userID, double money) {
        double currentScore = userAccountDAO.findOne(userID).getScore();

        if (money <= currentScore) {
            userAccountDAO.update(new UserAccount(userID, currentScore - money));
            log.info("Withdraw " + money + "$ from the account-" + userID);
        } else {
            log.warn("Cannot withdraw money from the account-" + userID);
            throw new IllegalArgumentException("Not enough money on account-" + userID);
        }
    }

    @Override
    public double getScore(long id) {
        return userAccountDAO.findOne(id).getScore();
    }

    @Override
    public void deleteAccount(long id) {
        userAccountDAO.delete(id);
    }

}
