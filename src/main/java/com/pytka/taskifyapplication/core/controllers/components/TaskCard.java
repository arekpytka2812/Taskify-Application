package com.pytka.taskifyapplication.core.controllers.components;


import com.pytka.taskifyapplication.TaskifyApplication;
import com.pytka.taskifyapplication.config.AppConfiguration;
import com.pytka.taskifyapplication.core.controllers.core.TaskPanel;
import com.pytka.taskifyapplication.core.models.TaskDTO;
import com.pytka.taskifyapplication.core.models.UserSettingsDTO;
import com.pytka.taskifyapplication.core.service.RequestService;
import com.pytka.taskifyapplication.core.service.TaskService;
import com.pytka.taskifyapplication.core.service.impl.TaskServiceImpl;
import com.pytka.taskifyapplication.utlis.PageNavigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;


@Setter
@Getter
public class TaskCard extends VBox {

    @FXML
    private Label taskNameLabel;

    @FXML
    private Label taskPriorityLabel;

    @FXML
    private Label finishTimeLabel;

    private TaskDTO task;

    private UserSettingsDTO userSettingsDTO;


    public TaskCard(){

        FXMLLoader fxmlLoader = new FXMLLoader(TaskifyApplication.class.getResource("/ui/components/TaskCard.fxml"));

        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TaskCard(TaskDTO task, UserSettingsDTO userSettingsDTO){

        this();

        this.task = task;

        this.taskNameLabel.setText(this.task.getName());
        this.taskPriorityLabel.setText(this.task.getPriority());

        this.userSettingsDTO = userSettingsDTO;

        this.setOnMouseClicked(this::openTaskPanel);
    }

    private void openTaskPanel(MouseEvent event) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);

        TaskService service = new TaskServiceImpl(
                context.getBean(RequestService.class)
        );

        PageNavigator.getInstance().push(new TaskPanel(service, task, userSettingsDTO));
    }

}
