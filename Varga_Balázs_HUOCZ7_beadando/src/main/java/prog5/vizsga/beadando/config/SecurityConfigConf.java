package prog5.vizsga.beadando.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

public interface SecurityConfigConf {
    void configure(AuthenticationManagerBuilder auth) throws Exception;
}
