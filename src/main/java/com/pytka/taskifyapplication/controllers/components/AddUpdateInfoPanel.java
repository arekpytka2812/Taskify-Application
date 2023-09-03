package com.pytka.taskifyapplication.controllers.components;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.controllers.ICenterPane;
import com.pytka.taskifyapplication.models.UpdateInfoDTO;
import com.pytka.taskifyapplication.services.TaskService;
import com.pytka.taskifyapplication.utlis.PageNavigator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.LocalDateTime;

public class AddUpdateInfoPanel extends VBox implements ICenterPane {

    @FXML
    private Label taskNameLabel;

    @FXML
    private TextArea updateArea;

    @FXML
    private Button saveButton;

    private Long taskID;
    private TaskService taskService;

    public AddUpdateInfoPanel(){

        FXMLLoader loader = new FXMLLoader(SpringMainApplication.class.getResource("/ui/components/AddUpdateInfoPanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AddUpdateInfoPanel(String taskName, Long taskID, TaskService taskService){

        this();

        this.taskService = taskService;
        this.taskID = taskID;

        taskNameLabel.setText(taskName);

        saveButton.setOnMouseClicked(this::saveButtonPressed);
    }

    private void saveButtonPressed(MouseEvent event){

        UpdateInfoDTO updateInfoDTO = UpdateInfoDTO.builder()
                .updateInfoDate(LocalDateTime.now())
                .description(updateArea.getText())
                .build();

        this.taskService.addUpdateInfo(taskID, updateInfoDTO);

        PageNavigator.getInstance().pop();
    }

    @Override
    public void refresh(){

    }

}
