package com.ivoronline.springboot_security_2fa.controllers;

import com.ivoronline.springboot_security_2fa.entities.Account;
import com.ivoronline.springboot_security_2fa.repositories.AccountRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MyController {

  //PROPERTIES
  private final AccountRepository   accountRepository;
  private final GoogleAuthenticator googleAuthenticator;

  //======================================================================
  // CONFIGURE (SERVICE & MOBILE APP)
  //======================================================================
  @RequestMapping("/Configure")
  public String configure(Model model){

    //GET USERNAME
    User user     = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = user.getUsername();

    //CREATES NEW KEY IN DB (everytime it is called)
    GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(username);

    //CONSTRUCT URL FOR QRCODE IMAGE
    String googleURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL("something", username, key);

    //ADD GOOGLE URL AS INPUT PARAMETER TO HTML PAGE
    model.addAttribute("googleURL", googleURL);

    //RETURN CONFIRMATION HTML PAGE
    return "Configure";

  }

  //======================================================================
  // ENTER CODE
  //======================================================================
  @RequestMapping("/EnterCode")
  public String enterCode(){
    return "EnterCode";
  }

  //======================================================================
  // VERIFY CODE
  //======================================================================
  @ResponseBody
  @RequestMapping("/VerifyCode")
  public String verifyCode(@RequestParam Integer code){

    //VERIFY CODE
    User    user      = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String  username  = user.getUsername();
    Boolean validCode = googleAuthenticator.authorizeUser(username, code);

    //RETURN IF CODE IS INVALID
    if (!validCode) { return "Code is Invalid"; }

    //UPDATE ACCOUNT
    Account account = accountRepository.findByUsername(user.getUsername());
            account.google2faAuthenticated = true;
            accountRepository.save(account);

    //RETURN MESSAGE TO USER
    return "Code is Valid";

  }

  //======================================================================
  // HELLO
  //======================================================================
  @ResponseBody
  @RequestMapping("/Hello")
  public String sayHello() {
    return "Hello from Controller";
  }

}
