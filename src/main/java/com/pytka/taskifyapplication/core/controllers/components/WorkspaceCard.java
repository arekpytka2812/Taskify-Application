package com.pytka.taskifyapplication.core.controllers.components;

import com.pytka.taskifyapplication.SpringMainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.io.IOException;

@Getter
public class WorkspaceCard extends Pane {

    @FXML
    private Label workspaceName;

    public WorkspaceCard(){
        FXMLLoader loader = new FXMLLoader(SpringMainApplication.class.getResource("/ui/components/WorkspaceCard.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
