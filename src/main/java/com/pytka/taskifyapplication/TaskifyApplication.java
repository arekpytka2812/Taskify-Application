package com.pytka.taskifyapplication;

import com.pytka.taskifyapplication.utlis.ParentLoader;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;


public class TaskifyApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        ApplicationContextInitializer<GenericApplicationContext> initializer =
                ac -> {
                    ac.registerBean(Application.class, () -> TaskifyApplication.this);
                    ac.registerBean(HostServices.class, this::getHostServices);
                    ac.registerBean(Parameters.class, this::getParameters);
                };

        this.context = new SpringApplicationBuilder()
                .sources(SpringMainApplication.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }


    @Override
    public void start(Stage stage) throws Exception {

        Parent parent = ParentLoader.loadParent(TaskifyApplication.class, "/ui/auth/RegisterPage.fxml", context);
        stage.setScene(new Scene(parent));
        stage.setTitle("TaskifyApp");
        stage.show();
    }

    @Override
    public void stop(){
        this.context.close();
        Platform.exit();
    }
}
