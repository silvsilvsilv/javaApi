package org.openjfx;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.geometry.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.openjfx.all.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GetAllScene 
{
    private static final String API_URL = "https://my-json-server.typicode.com/silvsilvsilv/simpleApi/db";


    public static Csc200 APIRequest()
    {
        try
        {
            // Step 1: Make HTTP request
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) 
            {
                response.append(line);
            }

            reader.close();
            connection.disconnect();
        
            // Step 2: Parse JSON with Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            Course course = objectMapper.readValue(response.toString(), Course.class);

            Csc200 csc200 = course.getCsc200();
            
            return csc200;

        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void display()
    {
        Stage window = new Stage();
        

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Get All Student Info");

        Label label = new Label();
        label.setText("Sections: ");
        Button showTableButton = new Button("GET");
        

        ChoiceBox<String> sectionChoices = new ChoiceBox<>();
        

        sectionChoices.getItems().addAll("Section A", "Section B", "Section C");
        sectionChoices.setValue("Section A");
        
        //Set Table Columns
        TableColumn<DatumA, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableView<DatumA> table = new TableView<>();
        // table.setItems(getSecA());
        table.getColumns().add(nameColumn);

        Csc200 csc200 = APIRequest();

        table.getItems().addAll(csc200.getSectionA().getData());

        

        VBox layout = new VBox();
        layout.setPadding(new Insets(0,0,10,0));
        layout.getChildren().addAll(label,sectionChoices);

        VBox newLayout = new VBox();
        newLayout.getChildren().addAll(layout,showTableButton);
        newLayout.setPadding(new Insets(20,20,20,20)); //top right bottom left
        newLayout.setAlignment(Pos.TOP_LEFT);

        showTableButton.setOnAction(event -> {
            String selectedSection = sectionChoices.getValue();
            if (selectedSection != null) 
            {
                if(selectedSection == "Section A")
                {
                    TableView<DatumA> tableView = createTableViewForSectionA(csc200);
                    newLayout.getChildren().add(tableView);
                }
                else if(selectedSection == "Section B")
                {
                    TableView<DatumB> tableView = createTableViewForSectionB(csc200);
                    newLayout.getChildren().add(tableView);
                }
                else if(selectedSection == "Section C")
                {
                    TableView<DatumC> tableView = createTableViewForSectionC(csc200);
                    newLayout.getChildren().add(tableView);
                }
                
            }
        });


        Scene scene = new Scene(newLayout,400,400);
        window.setScene(scene);
        window.showAndWait();
    }

    //For Section A
    private static TableView<DatumA> createTableViewForSectionA(Csc200 course) 
    {
        
        TableView<DatumA> tableView = new TableView<>();

        // Create a TableColumn for the "names" property
        TableColumn<DatumA, String> namesColumn = new TableColumn<>("Name");
        namesColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Add the column to the TableView
        tableView.getColumns().add(namesColumn);

        List<DatumA> data = course.getSectionA().getData(); 

        // Set items to the TableView
        tableView.getItems().addAll(data);

        return tableView;
    }

    //For Section B
    private static TableView<DatumB> createTableViewForSectionB(Csc200 course) 
    {
        
        TableView<DatumB> tableView = new TableView<>();

        // Create a TableColumn for the "names" property
        TableColumn<DatumB, String> namesColumn = new TableColumn<>("Name");
        namesColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Add the column to the TableView
        tableView.getColumns().add(namesColumn);

        List<DatumB> data = course.getSectionB().getData(); 

        // Set items to the TableView
        tableView.getItems().addAll(data);

        return tableView;
    }

    //For Section C
    private static TableView<DatumC> createTableViewForSectionC(Csc200 course) 
    {
        
        TableView<DatumC> tableView = new TableView<>();

        // Create a TableColumn for the "names" property
        TableColumn<DatumC, String> namesColumn = new TableColumn<>("Name");
        namesColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Add the column to the TableView
        tableView.getColumns().add(namesColumn);

        List<DatumC> data = course.getSectionC().getData(); 

        // Set items to the TableView
        tableView.getItems().addAll(data);

        return tableView;
    }
    
}
