package com.pytka.taskifyapplication.controllers;

import com.pytka.taskifyapplication.controllers.components.TaskCard;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class UIController {


    @FXML
    private HBox tasksContainer;

    private final ApplicationContext context;

    @FXML
    public void initialize(){
        for(int i = 0; i < 3; i++) {
            tasksContainer.getChildren().add(
                    new TaskCard("task " + i, "High", "12:23:12")
            );
        }
    }

}
