package com.pytka.taskifyapplication.controllers.components;

import com.pytka.taskifyapplication.TaskifyApplication;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import lombok.Setter;

import java.io.IOException;

@Setter
public class HideablePanel extends VBox {

    @FXML
    private VBox mainBox;

    @FXML
    private HBox bottomBox;

    @FXML
    private IconButton showButton;

    private boolean visibility = true;

    private TranslateTransition transition;

    public enum DirectionToVisibility {
        RIGHT,
        LEFT
    }

    private DirectionToVisibility direction = DirectionToVisibility.RIGHT;

    private int maxLeftX;
    private int maxRightX;

    public HideablePanel(){

        FXMLLoader loader = new FXMLLoader(TaskifyApplication.class.getResource("/ui/components/HideablePanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try{
            loader.load();
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }

        transition = new TranslateTransition(Duration.millis(200), this);

        mainBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("#0C8CE9"), null, null)));

        showButton.setOnClicked(this::switchVisibility);
    }

    public HideablePanel(boolean visibility, DirectionToVisibility direction, int maxLeftX, int maxRightX){
        this();
        this.visibility = visibility;
        this.direction = direction;
        this.maxLeftX = maxLeftX;
        this.maxRightX = maxRightX;

        if(direction == DirectionToVisibility.LEFT){
            bottomBox.setAlignment(Pos.CENTER_LEFT);
        }
        else{
            bottomBox.setAlignment(Pos.CENTER_RIGHT);
        }
    }

    public void repaint(String imagePath){

        showButton.setImageAndBackground(imagePath, new Background(new BackgroundFill(Paint.valueOf("#111111"), null, null)));

        if(direction == DirectionToVisibility.LEFT){
            bottomBox.setAlignment(Pos.CENTER_LEFT);
        }
        else{
            bottomBox.setAlignment(Pos.CENTER_RIGHT);
        }

        this.requestLayout();
    }


    private void switchVisibility(MouseEvent event){

        if(visibility){
            if(direction == DirectionToVisibility.LEFT){
                transition.setToX(maxRightX);
            }
            else{
                transition.setToX(maxLeftX);
            }
        }
        else{
            if(direction == DirectionToVisibility.LEFT){
                transition.setToX(maxLeftX);
            }
            else {
                transition.setToX(maxRightX);
            }
        }

        visibility = !visibility;

        transition.play();

        System.out.println("Switched: " + direction.toString() + " " + direction.toString() + "Panel");
    }



}
