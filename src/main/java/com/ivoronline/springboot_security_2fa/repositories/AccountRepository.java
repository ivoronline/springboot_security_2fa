package com.ivoronline.springboot_security_2fa.repositories;

import com.ivoronline.springboot_security_2fa.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {
  Account findByUsername(String Username);
}
