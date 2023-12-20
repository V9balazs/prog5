package prog5.vizsga.beadando.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import prog5.vizsga.beadando.view.LoginView;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends VaadinWebSecurity {

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

    @Bean
    public UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername("EmployeeA")
                        .password("employeeA")
                        .roles("EMPLOYEE")
                        .passwordEncoder(passwordEncoder::encode)
                        .build(),
                User.withUsername("EmployeeB")
                        .password("employeeB")
                        .roles("EMPLOYEE")
                        .passwordEncoder(passwordEncoder::encode)
                        .build(),
                User.withUsername("EmployeeC")
                        .password("employeeC")
                        .roles("EMPLOYEE")
                        .passwordEncoder(passwordEncoder::encode)
                        .build(),
                User.withUsername("Manager")
                        .password("manager")
                        .roles("MANAGER")
                        .passwordEncoder(passwordEncoder::encode)
                        .build());
    }
}