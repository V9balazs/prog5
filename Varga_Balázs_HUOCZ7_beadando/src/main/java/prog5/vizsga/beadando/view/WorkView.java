package prog5.vizsga.beadando.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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

@Route(value = "work", layout = MainLayout.class)
@PageTitle("Work Opportunities")
@PermitAll
public class WorkView extends VerticalLayout {

    private final JobService jobService;
    private Grid<JobOpportunity> grid;
    private boolean isManager;

    public WorkView(JobService jobService) {
        this.jobService = jobService;
        isManager = checkIfUserIsManager();

        add(new Span("Logged in: " + getCurrentUsername()));

        setSizeFull();
        configureGrid();
        add(grid);
        if (isManager) {
            addNewJobOpportunityButton();
        }
        updateList();
    }

    private void configureGrid() {
        grid = new Grid<>(JobOpportunity.class, false);
        grid.setSizeFull();


        if (isManager) {
            grid.addColumn(JobOpportunity::getId).setHeader("ID");
        }
        grid.addColumn(JobOpportunity::getDescription).setHeader("Description");
        grid.addColumn(JobOpportunity::getPlace).setHeader("Location");
        grid.addColumn(JobOpportunity::getApplicant).setHeader("Applicant");
        grid.addComponentColumn(jobOpportunity -> createActionButton(jobOpportunity)).setHeader("Actions");
    }

    private HorizontalLayout createActionButton(JobOpportunity jobOpportunity) {
        HorizontalLayout layout = new HorizontalLayout();
        if (isManager) {
            Button deleteButton = new Button("Delete", click -> deleteJobOpportunity(jobOpportunity));
            Button cancelButton = new Button("Cancel", click -> cancelApplication(jobOpportunity));
            Button acceptButton = new Button("Accept", click -> acceptJobOpportunity(jobOpportunity));
            layout.add(acceptButton, cancelButton, deleteButton);
            return layout;
        } else {
            Button applyButton = new Button("Apply", click -> applyForJob(jobOpportunity));
            layout.add(applyButton);
            return layout;
        }
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

    private void deleteJobOpportunity(JobOpportunity jobOpportunity) {
        jobService.deleteJobOpportunity(jobOpportunity.getId());
        updateList();
    }

    private void applyForJob(JobOpportunity jobOpportunity) {
        if (jobOpportunity.getApplicant() != null && !jobOpportunity.getApplicant().isEmpty()) {
            Notification.show("This job already has an applicant.", 3000, Notification.Position.MIDDLE);
            return;
        }

        jobService.applyForJob(jobOpportunity.getId(), getCurrentUsername());
        updateList();
    }

    private void addNewJobOpportunityButton() {
        Button newJobButton = new Button("New Work", click -> UI.getCurrent().navigate(NewWorkView.class));
        add(newJobButton);
    }

    private void acceptJobOpportunity(JobOpportunity jobOpportunity) {
        if (jobOpportunity.getApplicant() == null || jobOpportunity.getApplicant().isEmpty()) {
            Notification.show("No applicant present.", 3000, Notification.Position.MIDDLE);
            return;
        }

        jobService.acceptJobOpportunity(jobOpportunity.getId());
        updateList();
    }

    private void updateList() {
            grid.setItems(jobService.getAllJobOpportunities().stream()
                    .filter(jobOpportunity -> !jobOpportunity.isAccepted())
                    .collect(Collectors.toList()));
    }

    private void cancelApplication(JobOpportunity jobOpportunity) {
        jobService.cancelApplication(jobOpportunity.getId());
        updateList();
    }

    private boolean checkIfUserIsManager() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        return authentication.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_MANAGER"));
    }
}
