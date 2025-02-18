package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
public AccountService(AccountRepository accountRepository)
{
    this.accountRepository=accountRepository;
}


public Account registerAccount(String username, String password)
{
    if(!username.equals("")&&password.length()>4&&(accountRepository.findByUsername(username)==null))
    {
        return accountRepository.save(new Account(username, password));
    }

    return null;
}


public Account login(String username, String password)
{
    
        Optional<Account> ac=accountRepository.findByUsernameAndPassword(username, password);
            
        if(ac.isPresent())
        {
          return ac.get();
        }
        else{
            return null;
        }
}



}
