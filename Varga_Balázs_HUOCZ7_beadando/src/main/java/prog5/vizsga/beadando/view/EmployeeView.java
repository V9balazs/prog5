package prog5.vizsga.beadando.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import prog5.vizsga.beadando.entity.JobOpportunity;
import prog5.vizsga.beadando.service.JobService;

import java.util.stream.Collectors;

@Route(value = "employee", layout = MainLayout.class)
@PageTitle("Employee Dashboard")
@PermitAll
public class EmployeeView extends VerticalLayout {

    private final JobService jobService;
    private Grid<JobOpportunity> appliedGrid;
    private Grid<JobOpportunity> acceptedGrid;

    public EmployeeView(JobService jobService) {
        this.jobService = jobService;
        add(new Span("Logged in: " + getCurrentUsername()));
        setSizeFull();
        configureAppliedGrid();
        configureAcceptedGrid();
        add(appliedGrid, acceptedGrid);
        updateLists();
    }

    private void configureAppliedGrid() {
        appliedGrid = new Grid<>(JobOpportunity.class);
        appliedGrid.setSizeFull();
        appliedGrid.setColumns("Description", "Place", "Applicant");
        appliedGrid.addComponentColumn(this::createCancelButton).setHeader("Action");
    }

    private void configureAcceptedGrid() {
        acceptedGrid = new Grid<>(JobOpportunity.class);
        acceptedGrid.setSizeFull();
        acceptedGrid.setColumns("Description", "Place");
        acceptedGrid.addColumn(JobOpportunity::getApplicant).setHeader("Accepted");
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        else{
            return "Unknown";
        }
    }

    private Button createCancelButton(JobOpportunity jobOpportunity) {
        return new Button("Cancel", click -> cancelApplication(jobOpportunity));
    }

    private void cancelApplication(JobOpportunity jobOpportunity) {
        jobService.cancelApplication(jobOpportunity.getId());
        updateLists();
    }

    private void updateLists() {
        String currentUsername = getCurrentUsername();
        appliedGrid.setItems(jobService.getAllJobOpportunities().stream()
                .filter(job -> job.getApplicant() != null && job.getApplicant().equals(currentUsername) && !job.isAccepted())
                .collect(Collectors.toList()));
        acceptedGrid.setItems(jobService.getAllJobOpportunities().stream()
                .filter(job -> job.getApplicant() != null && job.getApplicant().equals(currentUsername) && job.isAccepted())
                .collect(Collectors.toList()));
    }
}
