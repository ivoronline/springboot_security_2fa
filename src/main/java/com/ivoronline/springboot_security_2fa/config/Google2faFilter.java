package com.ivoronline.springboot_security_2fa.config;

import com.ivoronline.springboot_security_2fa.entities.Account;
import com.ivoronline.springboot_security_2fa.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Google2faFilter extends GenericFilterBean {

  //PROPERTIES
  @Autowired private AccountRepository accountRepository;

  //=================================================================
  // DO FILTER
  //=================================================================
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest  request  = (HttpServletRequest)  servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    //ALLOW ACCESS TO FOLLOWING URLS
    RequestMatcher urlConfigure  = new AntPathRequestMatcher("/Configure");
    RequestMatcher urlEnterCode  = new AntPathRequestMatcher("/EnterCode");
    RequestMatcher urlVerifyCode = new AntPathRequestMatcher("/VerifyCode");
    if (urlConfigure.matches(request) || urlEnterCode.matches(request) || urlVerifyCode.matches(request)) {
      filterChain.doFilter(request, response);
      return;
    }

    //GET ACCOUNT
    User    user     = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String  username = user.getUsername();
    Account account  = accountRepository.findByUsername(username);

    //CHECK IF ACCOUNT SHOULD BE 2FA AUTHENTICATED
    if (account.google2faEnabled && !account.google2faAuthenticated) {
      System.out.println("Account is not 2FA Authenticated");
      return;
    }

    //ALLOW ACCESS IF ACCOUNT SHOULD NOT BE 2FA AUTHENTICATED
    filterChain.doFilter(request, response);
    return;

  }

}
