package com.example.springintroducehibernate.service;

public interface UserAccountService {

    void openAccount(long userID);

    void refillAccount(long userID, double money);

    void withdrawAccount(long userID, double money);

    double getScore(long id);

    void deleteAccount(long id);
}
