package org.nice.pokedexxfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new App(), 320, 240);
        scene.getStylesheets().add(
                Objects.requireNonNull( getClass().getResource("/globals.css").toExternalForm())
        );
        stage.setTitle("Pokedexx");
        stage.setScene(scene);
        stage.setMinHeight(720);
        stage.setMinWidth(1280);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}