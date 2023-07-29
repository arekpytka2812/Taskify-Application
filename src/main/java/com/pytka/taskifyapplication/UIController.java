package com.pytka.taskifyapplication;

import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UIController {

    @FXML
    private Button button;

    @FXML
    private Label label;

    private final ApplicationContext context;

    @FXML
    public void initialize(){
        this.button.setOnAction(event ->
            label.setText("Udalo sie")
        );
    }
}
