package prog5.vizsga.beadando.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import prog5.vizsga.beadando.entity.JobOpportunity;
import prog5.vizsga.beadando.service.JobService;

@Route(value = "new-work", layout = MainLayout.class)
@PageTitle("New Work")
@PermitAll
public class NewWorkView extends VerticalLayout {

    private final JobService jobService;

    private TextField descriptionField;
    private TextField placeField;

    public NewWorkView(JobService jobService) {
        this.jobService = jobService;
        addClassName("new-work-view");
        setSizeFull();

        add(createFormLayout());
        add(createSaveButton());
    }

    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();

        descriptionField = new TextField("Leírás");
        placeField = new TextField("Helyszín");

        formLayout.add(descriptionField, placeField);
        return formLayout;
    }

    private Button createSaveButton() {
        Button saveButton = new Button("Mentés", click -> saveJobOpportunity());
        saveButton.addClassName("save-button");
        return saveButton;
    }

    private void saveJobOpportunity() {
        JobOpportunity newJob = new JobOpportunity();
        newJob.setDescription(descriptionField.getValue());
        newJob.setPlace(placeField.getValue());

        jobService.createOrUpdateJobOpportunity(newJob);

        Notification.show("Munkalehetőség mentve.");
        descriptionField.clear();
        placeField.clear();
    }
}
