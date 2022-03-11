package com.ivoronline.springboot_security_2fa.config;

import com.ivoronline.springboot_security_2fa.entities.Account;
import com.ivoronline.springboot_security_2fa.repositories.AccountRepository;
import com.warrenstrange.googleauth.ICredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@RequiredArgsConstructor
@Component
public class GoogleCredentialRepository implements ICredentialRepository {

  //PROPERTIES
  private final AccountRepository accountRepository;

  //======================================================================
  // SAVE USER CREDENTIALS (STORE SECRET KEY)
  //======================================================================
  @Override
  public void saveUserCredentials(String userName, String secretKey, int validationCode, List<Integer> scratchCodes) {

    //GET ACCOUNT
    Account account = accountRepository.findByUsername(userName);
            account.google2faSecretKey = secretKey;
            account.google2faEnabled   = true;

    //STORE ACCOUNT
    accountRepository.save(account);

  }

  //======================================================================
  // GET SECRET KEY
  //======================================================================
  @Override
  public String getSecretKey(String userName) {

    //GET ACCOUNT
    Account account = accountRepository.findByUsername(userName);

    //RETURN SECRET KEY
    return account.google2faSecretKey;

  }

}
