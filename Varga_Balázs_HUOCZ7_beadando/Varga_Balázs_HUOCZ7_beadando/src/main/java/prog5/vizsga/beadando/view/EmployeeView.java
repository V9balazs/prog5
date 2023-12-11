package prog5.vizsga.beadando.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.access.annotation.Secured;
import prog5.vizsga.beadando.entity.JobOpportunity;
import prog5.vizsga.beadando.service.JobService;

@Route(value = "employee", layout = MainLayout.class)
@PageTitle("Employee Dashboard")
public class EmployeeView extends VerticalLayout {

    private final JobService jobService;
    private Grid<JobOpportunity> grid;
    private String currentEmployeeUsername; // Ezt valahonnan be kell állítani, pl. a bejelentkezett felhasználó alapján

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
        grid.setColumns("title", "description"); // Feltételezve, hogy a JobOpportunity entitásban vannak ilyen mezők
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList() {
        // Itt a jobService-t használva lekérdezzük az adott alkalmazott aktív munkáit
        // Például: jobService.getJobsForEmployee(currentEmployeeUsername)
        //grid.setItems(jobService.getJobsForEmployee(currentEmployeeUsername));
    }
}
