package com.ivoronline.springboot_security_2fa.controllers;

import com.ivoronline.springboot_security_2fa.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

  //PROPERTIES
  @Autowired AccountRepository accountRepository;

  //=================================================================
  // HELLO
  //=================================================================
  @ResponseBody
  @RequestMapping("Hello")
  String Hello() {
    return "Hello from Controller";
  }

}
