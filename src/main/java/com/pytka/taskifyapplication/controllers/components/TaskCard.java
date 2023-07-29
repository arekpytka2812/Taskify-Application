package com.pytka.taskifyapplication.controllers.components;


import com.pytka.taskifyapplication.TaskifyApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
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


    public TaskCard(){
        System.out.println("chuj");
        FXMLLoader fxmlLoader = new FXMLLoader(TaskifyApplication.class.getResource("/ui/components/TaskCard.fxml"));

        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        System.out.println("chuj 2");

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("chuj 23");
        this.setOnMouseClicked(event -> System.out.println("Yiiipee"));
    }

    public TaskCard(
            String taskNameLabel,
            String taskPriorityLabel,
            String finishTimeLabel
    ) {
        this();

        this.taskNameLabel.setText(taskNameLabel);
        this.taskPriorityLabel.setText(taskPriorityLabel);
        this.finishTimeLabel.setText(finishTimeLabel);

        System.out.println("chuj 4");

        this.setOnMouseClicked(event -> System.out.println("Yiiipee" + taskNameLabel));
    }
}
