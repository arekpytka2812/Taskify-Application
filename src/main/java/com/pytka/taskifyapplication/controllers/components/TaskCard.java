package com.pytka.taskifyapplication.controllers.components;


import com.pytka.taskifyapplication.TaskifyApplication;
import com.pytka.taskifyapplication.models.TaskDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import lombok.Setter;

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

    }


}
