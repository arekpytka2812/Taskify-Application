package com.pytka.taskifyapplication.utlis;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
public class ParentLoader {

    public static Parent loadParent(Class clazz, String fileName, ApplicationContext ac) {

        Parent root = null;
        try{
            FXMLLoader loader = new FXMLLoader(clazz.getResource(fileName));
            loader.setControllerFactory(ac::getBean);
            root = loader.load();
        }
        catch (IOException e){
            System.out.println("Man");
        }

        return root;
    }
}
