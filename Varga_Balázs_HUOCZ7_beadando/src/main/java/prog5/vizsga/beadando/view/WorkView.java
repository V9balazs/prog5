package prog5.vizsga.beadando.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import prog5.vizsga.beadando.entity.JobOpportunity;
import prog5.vizsga.beadando.service.JobService;

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
        grid.addColumn(JobOpportunity::getDescription).setHeader("Leírás");
        grid.addColumn(JobOpportunity::getPlace).setHeader("Helyszín");
        grid.addColumn(JobOpportunity::getApplicant).setHeader("Jelentkező");
        grid.addComponentColumn(jobOpportunity -> createActionButton(jobOpportunity)).setHeader("Műveletek");
    }

    private Button createActionButton(JobOpportunity jobOpportunity) {
        if (isManager) {
            return new Button("Törlés", click -> deleteJobOpportunity(jobOpportunity));
        } else {
            return new Button("Jelentkezés", click -> applyForJob(jobOpportunity));
        }
    }

    private void deleteJobOpportunity(JobOpportunity jobOpportunity) {
        jobService.deleteJobOpportunity(jobOpportunity.getId());
        updateList();
    }

    private void applyForJob(JobOpportunity jobOpportunity) {
        jobService.applyForJob(jobOpportunity.getId(), getCurrentEmployeeUsername());
        updateList();
    }

    private void addNewJobOpportunityButton() {
        Button newJobButton = new Button("Új Munkalehetőség", click -> UI.getCurrent().navigate(NewWorkView.class));
        add(newJobButton);
    }

    private void updateList() {
        grid.setItems(jobService.getAllJobOpportunities());
    }

    private boolean checkIfUserIsManager() {
        // Implementáld a logikát, ami eldönti, hogy a jelenlegi felhasználó manager-e
        return false; // Ideiglenes érték
    }

    private String getCurrentEmployeeUsername() {
        // Visszaadja a jelenlegi bejelentkezett alkalmazott felhasználónevét
        return "employeeUsername"; // Ideiglenes érték
    }
}
