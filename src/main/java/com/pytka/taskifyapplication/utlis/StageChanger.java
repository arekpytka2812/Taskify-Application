package com.pytka.taskifyapplication.utlis;

import com.pytka.taskifyapplication.SpringMainApplication;
import com.pytka.taskifyapplication.TaskifyApplication;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class StageChanger {
    public static Stage changeStage(ActionEvent event, String filePath, ApplicationContext ac){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Parent parent = null;
        FXMLLoader loader = null;

        try{
            loader = new FXMLLoader(TaskifyApplication.class.getResource(filePath));
            loader.setControllerFactory(ac::getBean);
            parent = loader.load();
        }
        catch (IOException e){
            System.out.println("Man");
        }

        stage.setScene(new Scene(
                parent,
                SpringMainApplication.MAX_SCREEN_WIDTH,
                SpringMainApplication.MAX_SCREEN_HEIGHT
        ));

        stage.getScene().setUserData(loader.getController());

        return stage;
    }
}
