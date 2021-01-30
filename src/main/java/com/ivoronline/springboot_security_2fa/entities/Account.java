package com.ivoronline.springboot_security_2fa.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;
  public String  username;
  public String  password;
  public String  role;

  public String  google2faSecretKey;
  public Boolean google2faEnabled       = false;
  public Boolean google2faAuthenticated = false;

}
