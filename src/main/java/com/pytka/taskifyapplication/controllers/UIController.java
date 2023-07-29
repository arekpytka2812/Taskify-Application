package com.pytka.taskifyapplication.controllers;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.controllers.components.HideablePanel;
import com.pytka.taskifyapplication.controllers.components.TaskCard;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.chrono.HijrahDate;


@Component
@RequiredArgsConstructor
public class UIController {

    @FXML
    private HideablePanel leftPanel;

    @FXML
    private HideablePanel rightPanel;

    @FXML
    public void initialize(){
        leftPanel.setDirection(HideablePanel.DirectionToVisibility.RIGHT);
        leftPanel.setMaxLeftX(-350);
        leftPanel.setMaxRightX(0);
        rightPanel.setDirection(HideablePanel.DirectionToVisibility.LEFT);
        rightPanel.setMaxRightX(SpringMainApplication.CURRENT_SCREEN_WIDTH - 50);
        rightPanel.setMaxLeftX(SpringMainApplication.CURRENT_SCREEN_WIDTH - 400);

        leftPanel.repaint();
        rightPanel.repaint();
    }

}
