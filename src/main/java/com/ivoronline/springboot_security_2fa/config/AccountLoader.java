package com.ivoronline.springboot_security_2fa.config;

import com.ivoronline.springboot_security_2fa.entities.Account;
import com.ivoronline.springboot_security_2fa.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AccountLoader implements CommandLineRunner {

  @Autowired
  private AccountRepository accountRepository;

  @Override
  @Transactional
  public void run(String... args) throws Exception {

    //CREATE ACCOUNT
    Account account           = new Account();
            account.username  = "john";
            account.password  = "johnpassword";
            account.role      = "ROLE_ADMIN";

    //STORE ACCOUNT INTO DB
    accountRepository.save(account);

  }

}
