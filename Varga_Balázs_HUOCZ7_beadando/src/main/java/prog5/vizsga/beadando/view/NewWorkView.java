package prog5.vizsga.beadando.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import prog5.vizsga.beadando.entity.JobOpportunity;
import prog5.vizsga.beadando.service.JobService;

@Route(value = "new-work", layout = MainLayout.class)
@PageTitle("New Work")
@RolesAllowed("MANAGER")
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

        descriptionField = new TextField("Description");
        placeField = new TextField("Location");

        formLayout.add(descriptionField, placeField);
        return formLayout;
    }

    private Button createSaveButton() {
        Button saveButton = new Button("Save", click -> saveJobOpportunity());
        saveButton.addClassName("save-button");
        return saveButton;
    }

    private void saveJobOpportunity() {
        if (descriptionField.isEmpty() || placeField.isEmpty()) {
            Notification.show("Please fill out every field!", 3000, Notification.Position.MIDDLE);
            return;
        }

        JobOpportunity newJob = new JobOpportunity();
        newJob.setDescription(descriptionField.getValue());
        newJob.setPlace(placeField.getValue());

        jobService.createOrUpdateJobOpportunity(newJob);

        Notification.show("New work saved!");
        descriptionField.clear();
        placeField.clear();
    }
}
