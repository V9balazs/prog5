package prog5.vizsga.beadando.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import prog5.vizsga.beadando.entity.JobOpportunity;
import prog5.vizsga.beadando.helper.ViewHelper;
import prog5.vizsga.beadando.service.JobService;
import prog5.vizsga.beadando.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Route(value = "work", layout = MainLayout.class)
@PageTitle("Work Opportunities")
@PermitAll
public class WorkView extends VerticalLayout {

    private final JobService jobService;
    private Grid<JobOpportunity> grid;
    private boolean isManager;
    private TextField descriptionFilter;
    private TextField locationFilter;
    private final UserService userService;

    public WorkView(JobService jobService, UserService userService) {
        this.jobService = jobService;
        this.userService = userService;
        isManager = ViewHelper.checkIfUserIsManager();

        add(new Span("Logged in: " + ViewHelper.getCurrentUsername()));

        setSizeFull();
        add(createFilterLayout());
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

    private HorizontalLayout createFilterLayout() {
        descriptionFilter = new TextField();
        descriptionFilter.setPlaceholder("Filter by Description");

        locationFilter = new TextField();
        locationFilter.setPlaceholder("Filter by Location");

        Button filterButton = new Button("Search", click -> updateList());

        HorizontalLayout filterLayout = new HorizontalLayout(descriptionFilter, locationFilter, filterButton);
        return filterLayout;
    }

    private void deleteJobOpportunity(JobOpportunity jobOpportunity) {
        jobService.deleteJobOpportunity(jobOpportunity.getId());
        updateList();
    }

    private void applyForJob(JobOpportunity jobOpportunity) {
        if (jobOpportunity.getApplicant() != null && !jobOpportunity.getApplicant().isEmpty()) {
            ViewHelper.showNotification("This job already has an applicant.");
            return;
        }

        String currentUsername = ViewHelper.getCurrentUsername();
        String userId = userService.getUserIdFromUsername(currentUsername);
        String applicantInfo = currentUsername + " (" + userId + ")";

        jobService.applyForJob(jobOpportunity.getId(), applicantInfo);
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
        String descriptionFilterValue = descriptionFilter.getValue().toLowerCase();
        String locationFilterValue = locationFilter.getValue().toLowerCase();

        List<JobOpportunity> filteredJobs = jobService.getAllJobOpportunities().stream()
                .filter(job -> !job.isAccepted())
                .filter(job -> job.getDescription().toLowerCase().contains(descriptionFilterValue))
                .filter(job -> job.getPlace().toLowerCase().contains(locationFilterValue))
                .collect(Collectors.toList());

        grid.setItems(filteredJobs);
    }

    private void cancelApplication(JobOpportunity jobOpportunity) {
        jobService.cancelApplication(jobOpportunity.getId());
        updateList();
    }
}
