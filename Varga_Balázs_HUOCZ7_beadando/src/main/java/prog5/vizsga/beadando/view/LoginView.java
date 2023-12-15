package prog5.vizsga.beadando.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import prog5.vizsga.beadando.service.SecurityService;

import java.util.Collection;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final SecurityService securityService;

    @Autowired
    public LoginView(SecurityService securityService) {
        this.securityService = securityService;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        H1 title = new H1("Bejelentkezés");
        LoginForm loginForm = new LoginForm();
        loginForm.setAction("login");

        add(title, loginForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        UserDetails user = securityService.getAuthenticatedUser();
        if (user != null) {
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            if (authorities.contains(new SimpleGrantedAuthority("MANAGER"))) {
                beforeEnterEvent.forwardTo(WorkView.class);
            } else if (authorities.contains(new SimpleGrantedAuthority("EMPLOYEE"))) {
                beforeEnterEvent.forwardTo(EmployeeView.class);
            }
        }
    }
}
