package com.ucsal.accountservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ucsal.accountservice.model.Account;

@Service
public class AccountService {

    // Lista simulando um "banco" temporário
    private List<Account> accounts = new ArrayList<>();

    // Construtor com alguns dados iniciais
    public AccountService() {
        accounts.add(new Account(1L, "Maria", 1000.0));
        accounts.add(new Account(2L, "João", 2500.0));
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Account getAccountById(Long id) {
        return accounts.stream()
                .filter(acc -> acc.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Account createAccount(Account acc) {
        accounts.add(acc);
        return acc;
    }
}