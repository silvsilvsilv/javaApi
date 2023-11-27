package org.openjfx;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class App extends Application  {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Group 15 REST Api Client");

        BorderPane border = new BorderPane();
        HBox hbox = addHBox();
        Label label = new Label("Please choose");
        hbox.setAlignment(Pos.CENTER);
        
        VBox vbox = new VBox();

        vbox.getChildren().addAll(label,hbox);
        vbox.setAlignment(Pos.CENTER);
        

        border.setCenter(vbox);

        Scene scene = new Scene(border, 500,300);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        
        Button getAllButton = new Button("Get All Students Info");
        getAllButton.setOnAction(e -> GetAllScene.display());
        Button getStudentInfoButton = new Button("Get One Student Info");
        getStudentInfoButton.setOnAction(e -> new GetOneScene().display());
    

        hbox.getChildren().addAll(getAllButton, getStudentInfoButton);

        return hbox;
    }

}


