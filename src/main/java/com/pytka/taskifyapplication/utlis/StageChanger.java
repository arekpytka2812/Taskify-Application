package com.pytka.taskifyapplication.utlis;

import com.pytka.taskifyapplication.TaskifyApplication;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;

public class StageChanger {
    public static Stage changeStage(ActionEvent event, String filePath, ApplicationContext ac){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(ParentLoader.loadParent(
                TaskifyApplication.class,
                filePath,
                ac
        )));

        return stage;
    }
}
