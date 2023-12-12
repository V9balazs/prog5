package prog5.vizsga.beadando.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import prog5.vizsga.beadando.entity.JobOpportunity;
import prog5.vizsga.beadando.service.JobService;

@Route(value = "employee", layout = MainLayout.class)
@PageTitle("Employee Dashboard")
@PermitAll
public class EmployeeView extends VerticalLayout {

    private final JobService jobService;
    private Grid<JobOpportunity> grid;

    public EmployeeView(JobService jobService) {
        this.jobService = jobService;
        setSizeFull();
        configureGrid();
        add(grid);
        updateList();
    }

    private void configureGrid() {
        grid = new Grid<>(JobOpportunity.class);
        grid.setSizeFull();
        grid.setColumns("id", "description", "place");
        grid.addComponentColumn(this::createApplyButton).setHeader("Actions");
    }

    private Button createApplyButton(JobOpportunity jobOpportunity) {
        Button applyButton = new Button("Jelentkezés", click -> applyForJob(jobOpportunity));
        applyButton.setEnabled(!isCurrentUserApplied(jobOpportunity));
        return applyButton;
    }

    private void applyForJob(JobOpportunity jobOpportunity) {
        jobService.applyForJob(jobOpportunity.getId(), getCurrentEmployeeUsername());
        updateList();
    }

    private boolean isCurrentUserApplied(JobOpportunity jobOpportunity) {
        String currentUsername = getCurrentEmployeeUsername();
        return jobOpportunity.getApplicant() != null && jobOpportunity.getApplicant().equals(currentUsername);
    }

    private String getCurrentEmployeeUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private void updateList() {
        grid.setItems(jobService.getAllJobOpportunities());
    }
}
