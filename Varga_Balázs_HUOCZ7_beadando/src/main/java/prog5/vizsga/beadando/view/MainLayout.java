package prog5.vizsga.beadando.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import prog5.vizsga.beadando.service.SecurityService;

@CssImport("./styles/styles.css")
public class MainLayout extends AppLayout implements AfterNavigationObserver {
    private final SecurityService securityService;
    private RouterLink listLink;
    private RouterLink employeeLink;


    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Foreign work management");
        logo.addClassName("small-logo");

        Button logoutButton = new Button("Logout", e -> securityService.logout());
        logoutButton.addClassName("logout-button");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");

        header.addAndExpand(logo);
        header.add(logoutButton);

        addToNavbar(header);
    }

    private void createDrawer() {
        listLink = new RouterLink("Job Opportunities", WorkView.class);
        employeeLink = new RouterLink("Employee Dashboard", EmployeeView.class);

        addToDrawer(new VerticalLayout(
                listLink,
                employeeLink
        ));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        boolean isWorkView = event.getActiveChain().stream()
                .anyMatch(component -> component.getClass().equals(WorkView.class));
        boolean isEmployeeView = event.getActiveChain().stream()
                .anyMatch(component -> component.getClass().equals(EmployeeView.class));

        listLink.getElement().getClassList().set("active", isWorkView);
        employeeLink.getElement().getClassList().set("active", isEmployeeView);
    }
}
