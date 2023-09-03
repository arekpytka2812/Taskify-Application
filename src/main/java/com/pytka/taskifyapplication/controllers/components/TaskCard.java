package com.pytka.taskifyapplication.controllers.components;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pytka.taskifyapplication.TaskifyApplication;
import com.pytka.taskifyapplication.config.AppConfiguration;
import com.pytka.taskifyapplication.controllers.core.TaskPanel;
import com.pytka.taskifyapplication.models.TaskDTO;
import com.pytka.taskifyapplication.services.RequestService;
import com.pytka.taskifyapplication.services.TaskService;
import com.pytka.taskifyapplication.services.impl.RequestServiceImpl;
import com.pytka.taskifyapplication.services.impl.TaskServiceImpl;
import com.pytka.taskifyapplication.utlis.PageNavigator;
import com.pytka.taskifyapplication.utlis.ParentLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;


@Setter
@Getter
public class TaskCard extends StackPane {

    @FXML
    private Label taskNameLabel;

    @FXML
    private Label taskPriorityLabel;

    @FXML
    private Label finishTimeLabel;

    private TaskDTO task;


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

    public TaskCard(TaskDTO task){

        this();

        this.task = task;

        this.taskNameLabel.setText(this.task.getName());
        this.taskPriorityLabel.setText(this.task.getPriority());

        this.setOnMouseClicked(this::openTaskPanel);
    }

    private void openTaskPanel(MouseEvent event) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);

        TaskService service = new TaskServiceImpl(
                context.getBean(RequestService.class)
        );

        PageNavigator.getInstance().push(new TaskPanel(service, task));
    }

}
