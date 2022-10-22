package com.estudo.esqueleto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/estudo/esqueleto/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 631, 571);
        stage.setTitle("Feriados");
        stage.setScene(scene);
        URL icone = HelloApplication.class.getResource("/css/icone.png");
        stage.getIcons().add(new Image(icone.openStream()));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}