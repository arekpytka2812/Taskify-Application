package com.pytka.taskifyapplication.controllers.core;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.controllers.ICenterPane;
import com.pytka.taskifyapplication.models.WorkspaceDTO;
import com.pytka.taskifyapplication.models.WorkspaceLiteDTO;
import com.pytka.taskifyapplication.services.WorkspaceService;
import com.pytka.taskifyapplication.utlis.PageNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
public class WorkspacePanel extends VBox implements ICenterPane{

    @FXML
    private TextField nameField;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorLabel;

    private final WorkspaceService workspaceService;

    private final MainFrameController mainFrame;


    @SneakyThrows
    public WorkspacePanel(WorkspaceService service, MainFrameController mainFrame){

        this.workspaceService = service;
        this.mainFrame = mainFrame;

        FXMLLoader loader = new FXMLLoader(SpringMainApplication.class.getResource("/ui/core/WorkspacePanel.fxml"));
        loader.setController(this);
        loader.setRoot(this);

        loader.load();

        saveButton.setOnAction(this::onSaveButtonPressed);

    }

    private void onSaveButtonPressed(ActionEvent e){

        if(nameField.getText().isEmpty()){
            errorLabel.setText("You need to type workspace name!");
            return;
        }

        errorLabel.setText("");

        WorkspaceLiteDTO workspace = WorkspaceLiteDTO.builder()
                .name(nameField.getText())
                .build();

        this.workspaceService.addWorkspace(workspace);

        PageNavigator.getInstance().pop();

        mainFrame.refresh();
    }

    @Override
    public void refresh() {

    }
}
