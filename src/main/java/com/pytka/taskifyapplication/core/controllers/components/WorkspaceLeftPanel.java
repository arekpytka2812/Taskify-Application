package com.pytka.taskifyapplication.core.controllers.components;

import com.pytka.taskifyapplication.SpringMainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
public class WorkspaceLeftPanel extends VBox {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox workspacesBox;

    @FXML
    private Button addWorkspaceButton;

    public WorkspaceLeftPanel(){

        FXMLLoader loader = new FXMLLoader(SpringMainApplication.class.getResource("/ui/components/WorkspaceLeftPanel.fxml"));

        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateWorkspaces(List<WorkspaceCard> workspaceCards){

        if(!workspacesBox.getChildren().isEmpty()){
            workspacesBox.getChildren().clear();
        }

        workspacesBox.getChildren().addAll(workspaceCards);
    }

    public void addWorkspace(WorkspaceCard workspaceCard){
        workspacesBox.getChildren().add(workspaceCard);
    }


    public void clear(){

        workspacesBox.getChildren().clear();
    }
}
