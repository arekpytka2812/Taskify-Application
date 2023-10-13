package com.pytka.taskifyapplication.core.controllers.components;

import com.pytka.taskifyapplication.TaskifyApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Setter
@Getter
public class SidePanel extends VBox {

    @FXML
    private VBox mainBox;

    @FXML
    private HBox bottomBox;

    @FXML
    private IconButton showButton;

    public enum PanelSide {
        RIGHT,
        LEFT
    }

    private PanelSide side = PanelSide.RIGHT;

    private int maxLeftX;
    private int maxRightX;

    @FXML
    private void initialize(){

        this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#0C8CE9"), null, null)));
    }


    public SidePanel(){

        FXMLLoader loader = new FXMLLoader(TaskifyApplication.class.getResource("/ui/components/SidePanel.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try{
            loader.load();
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }


        mainBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("#0C8CE9"), null, null)));

    }

    public SidePanel(boolean visibility, PanelSide side, int maxLeftX, int maxRightX){
        this();
        this.side = side;
        this.maxLeftX = maxLeftX;
        this.maxRightX = maxRightX;

        if(side == PanelSide.LEFT){
            bottomBox.setAlignment(Pos.CENTER_LEFT);
        }
        else{
            bottomBox.setAlignment(Pos.CENTER_RIGHT);
        }
    }

    public void repaint(String imagePath){

        showButton.setImageAndBackground(imagePath, new Background(new BackgroundFill(Paint.valueOf("#111111"), null, null)));

        if(side == PanelSide.LEFT){
            bottomBox.setAlignment(Pos.CENTER_RIGHT);
        }
        else{
            bottomBox.setAlignment(Pos.CENTER_LEFT);
        }

    }

    public void setBottomBoxAlignment(PanelSide side){

        this.side = side;

        if(side == PanelSide.LEFT){
            bottomBox.setAlignment(Pos.CENTER_RIGHT);
        }
        else{
            bottomBox.setAlignment(Pos.CENTER_LEFT);
        }

    }
}
