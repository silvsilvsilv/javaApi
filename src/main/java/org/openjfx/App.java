package org.openjfx;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.openjfx.all.*;
import org.openjfx.search.*;

public class App extends Application  {

    Button button;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("New Title");

        button = new Button();
        button.setText("Click me");
        button.setOnAction(e -> System.out.println("A"));

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 300,300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // i have no idea what this does but it mitigates an error with PrimaryController and Secondary Controller
    public static void setRoot(String string) {
    }
}


