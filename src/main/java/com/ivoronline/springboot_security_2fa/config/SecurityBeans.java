package com.ivoronline.springboot_security_2fa.config;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;
import com.warrenstrange.googleauth.ICredentialRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;

@Configuration
public class SecurityBeans {

  //=================================================================
  // GOOGLE AUTHENTICATOR
  //=================================================================
  @Bean
  public GoogleAuthenticator googleAuthenticator(ICredentialRepository credentialRepository){

    GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder configBuilder = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder();

    configBuilder
      .setTimeStepSizeInMillis(TimeUnit.SECONDS.toMillis(30))
      .setWindowSize(10)
      .setNumberOfScratchCodes(0);

    GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator(configBuilder.build());
                        googleAuthenticator.setCredentialRepository(credentialRepository);

    return googleAuthenticator;

  }

}
