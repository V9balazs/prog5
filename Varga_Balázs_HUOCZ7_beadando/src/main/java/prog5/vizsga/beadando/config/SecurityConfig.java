package prog5.vizsga.beadando.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import prog5.vizsga.beadando.service.CustomUserDetailsService;
import prog5.vizsga.beadando.view.LoginView;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends VaadinWebSecurity implements SecurityConfigConf {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        setLoginView(http, LoginView.class);

        http.authorizeHttpRequests()
                .requestMatchers(new AntPathRequestMatcher("/public/**")).permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/work", true)
                .permitAll();

        super.configure(http);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}