package com.pytka.taskifyapplication.controllers.components;

import com.pytka.taskifyapplication.SpringMainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UpdateInfoCard extends VBox {

    @FXML
    private Label updateInfoDateLabel;

    @FXML
    private TextField description;

    public UpdateInfoCard(){
        FXMLLoader loader = new FXMLLoader(SpringMainApplication.class.getResource("/ui/components/UpdateInfoCard.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public UpdateInfoCard(String updateInfoDate, String description){
        this();

        this.updateInfoDateLabel.setText(updateInfoDate);
        this.description.setText(description);
    }

}

