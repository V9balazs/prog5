package prog5.vizsga.beadando.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import prog5.vizsga.beadando.entity.JobOpportunity;
import prog5.vizsga.beadando.helper.ViewHelper;
import prog5.vizsga.beadando.service.JobService;

import java.util.stream.Collectors;

@Route(value = "employee", layout = MainLayout.class)
@PageTitle("Employee Dashboard")
@PermitAll
public class EmployeeView extends VerticalLayout {

    private final JobService jobService;
    private Grid<JobOpportunity> appliedGrid;
    private Grid<JobOpportunity> acceptedGrid;
    private Grid<JobOpportunity> managerGrid;
    private boolean isManager;

    public EmployeeView(JobService jobService) {
        this.jobService = jobService;
        isManager = ViewHelper.checkIfUserIsManager();

        add(new Span("Logged in: " + ViewHelper.getCurrentUsername()));
        setSizeFull();

        if (isManager) {
            configureManagerGrid();
            add(managerGrid);
        } else {
            configureAppliedGrid();
            configureAcceptedGrid();
            add(new H3("Applied for"), appliedGrid, new H3("Accepted Jobs"), acceptedGrid);
        }

        updateLists();
    }

    private void configureAppliedGrid() {
        appliedGrid = new Grid<>(JobOpportunity.class, false);
        appliedGrid.setSizeFull();
        appliedGrid.addColumn(JobOpportunity::getDescription).setHeader("Description");
        appliedGrid.addColumn(JobOpportunity::getPlace).setHeader("Location");
        appliedGrid.addColumn(JobOpportunity::getApplicant).setHeader("Applicant");
        appliedGrid.addComponentColumn(this::createCancelButton).setHeader("Action");
    }

    private void configureAcceptedGrid() {
        acceptedGrid = new Grid<>(JobOpportunity.class, false);
        acceptedGrid.setSizeFull();
        acceptedGrid.addColumn(JobOpportunity::getDescription).setHeader("Description");
        acceptedGrid.addColumn(JobOpportunity::getPlace).setHeader("Location");
        acceptedGrid.addColumn(JobOpportunity::getApplicant).setHeader("Accepted");
    }

    private void configureManagerGrid() {
        managerGrid = new Grid<>(JobOpportunity.class, false);
        managerGrid.setSizeFull();
        managerGrid.addColumn(JobOpportunity::getId).setHeader("ID");
        managerGrid.addColumn(JobOpportunity::getDescription).setHeader("Description");
        managerGrid.addColumn(JobOpportunity::getPlace).setHeader("Location");
        managerGrid.addColumn(JobOpportunity::getApplicant).setHeader("Accepted");
    }

    private Button createCancelButton(JobOpportunity jobOpportunity) {
        return new Button("Cancel", click -> cancelApplication(jobOpportunity));
    }

    private void cancelApplication(JobOpportunity jobOpportunity) {
        jobService.cancelApplication(jobOpportunity.getId());
        updateLists();
    }

    private String extractUsernameFromApplicant(String applicant) {
        if (applicant != null && applicant.contains(" (")) {
            return applicant.substring(0, applicant.indexOf(" ("));
        }
        return null;
    }

    private void updateLists() {
        String currentUsername = ViewHelper.getCurrentUsername();
        if (isManager) {
            managerGrid.setItems(jobService.getAllJobOpportunities().stream()
                    .filter(JobOpportunity::isAccepted)
                    .collect(Collectors.toList()));
        } else {
            appliedGrid.setItems(jobService.getAllJobOpportunities().stream()
                    .filter(job -> job.getApplicant() != null &&
                            extractUsernameFromApplicant(job.getApplicant()) != null &&
                            extractUsernameFromApplicant(job.getApplicant()).equals(currentUsername) &&
                            !job.isAccepted())
                    .collect(Collectors.toList()));
            acceptedGrid.setItems(jobService.getAllJobOpportunities().stream()
                    .filter(job -> job.getApplicant() != null &&
                            extractUsernameFromApplicant(job.getApplicant()) != null &&
                            extractUsernameFromApplicant(job.getApplicant()).equals(currentUsername) &&
                            job.isAccepted())
                    .collect(Collectors.toList()));
        }
    }

}
