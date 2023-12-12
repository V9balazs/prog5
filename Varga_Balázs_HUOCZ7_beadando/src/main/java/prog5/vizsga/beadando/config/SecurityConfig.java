package prog5.vizsga.beadando.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        http.authorizeHttpRequests(customizer -> customizer.requestMatchers(new AntPathRequestMatcher("/public/**"))
                .permitAll());

        super.configure(http);

        this.setLoginView(http, LoginView.class);
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
                        .password("employeec")
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