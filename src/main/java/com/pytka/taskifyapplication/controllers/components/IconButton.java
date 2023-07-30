package com.pytka.taskifyapplication.controllers.components;

import com.pytka.taskifyapplication.TaskifyApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import org.yaml.snakeyaml.nodes.AnchorNode;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IconButton extends AnchorPane {

    @FXML
    public ImageView image;

    private Background background;

    private String imagePath;

    public IconButton(){
        FXMLLoader loader = new FXMLLoader(TaskifyApplication.class.getResource("/ui/components/IconButton.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try{
            loader.load();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void setImageAndBackground(String imagePath, Background background){

        this.getChildren().clear();

        this.imagePath = imagePath;
        this.image = new ImageView(new Image(imagePath, 50, 50, true, true));

        this.background = background;

        this.requestLayout();

        this.getChildren().add(image);
    }

    public void setOnClicked(EventHandler<? super MouseEvent> event){
        this.setOnMouseClicked(event);
    }


}
