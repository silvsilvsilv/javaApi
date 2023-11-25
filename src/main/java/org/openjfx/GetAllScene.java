package org.openjfx;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;
import java.util.stream.Collectors;
import javafx.util.Callback;
import javafx.util.StringConverter;

import org.openjfx.all.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GetAllScene 
{
    //private static final String API_URL = "https://my-json-server.typicode.com/silvsilvsilv/simpleApi/db";


    public static Csc200 APIRequest()
    {
        try
        {
            // Step 1: Make HTTP request
            // URL url = new URL(API_URL);
            // HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // connection.setRequestMethod("GET");

            // // Read the response
            // BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            // StringBuilder response = new StringBuilder();
            // String line;

            // while ((line = reader.readLine()) != null) 
            // {
            //     response.append(line);
            // }

            // reader.close();
            // connection.disconnect();
            String response = "{\"csc200\":{\"section-a\":{\"total_students\":30,\"data\":[{\"id\":1,\"name\":\"Almirante, Jann Louie Q.\",\"attendance\":[{\"is_present\":true,\"date\":\"2023-09-20\"},{\"is_present\":true,\"date\":\"2023-09-21\"}]},{\"id\":2,\"name\":\"Arceño, Jayson E.\",\"attendance\":[{\"is_present\":true,\"date\":\"2023-09-20\"},{\"is_present\":true,\"date\":\"2023-09-21\"}]}]},\"section-b\":{\"total_students\":31,\"data\":[{\"id\":1,\"name\":\"Aniñon, Ryan S.\",\"attendance\":[{\"is_present\":true,\"date\":\"2023-09-20\"},{\"is_present\":true,\"date\":\"2023-09-21\"}]},{\"id\":2,\"name\":\"Banjao, Maven\",\"attendance\":[{\"is_present\":true,\"date\":\"2023-09-20\"},{\"is_present\":true,\"date\":\"2023-09-21\"}]}]},\"section-c\":{\"total_students\":21,\"data\":[{\"id\":1,\"name\":\"Abrenica, Jay Jovi James B.\",\"attendance\":[{\"is_present\":true,\"date\":\"2023-09-20\"},{\"is_present\":true,\"date\":\"2023-09-21\"}]},{\"id\":2,\"name\":\"Abria, Mary Joy A.\",\"attendance\":[{\"is_present\":true,\"date\":\"2023-09-20\"},{\"is_present\":true,\"date\":\"2023-09-21\"}]}]}}}";

        
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

        Csc200 csc200 = APIRequest();

        VBox layout = new VBox();
        layout.setPadding(new Insets(0,0,10,0));
        layout.getChildren().addAll(label,sectionChoices);

        VBox newLayout = new VBox();
        newLayout.getChildren().addAll(layout,showTableButton);
        newLayout.setPadding(new Insets(20,20,20,20));
        newLayout.setAlignment(Pos.TOP_LEFT);

        showTableButton.setOnAction(event -> {
            String selectedSection = sectionChoices.getValue();
            if (selectedSection != null) 
            {
                if(selectedSection == "Section A")
                {
                    TableView<DatumA> tableView = createTableViewForSectionA(csc200);

                    // Clear existing children before adding the new TableView
                    newLayout.getChildren().removeIf(node -> node instanceof TableView);
                    newLayout.getChildren().add(tableView);
                }
                else if(selectedSection == "Section B")
                {
                    TableView<DatumB> tableView = createTableViewForSectionB(csc200);
                    newLayout.getChildren().removeIf(node -> node instanceof TableView);
                    newLayout.getChildren().add(tableView);
                }
                else if(selectedSection == "Section C")
                {
                    TableView<DatumC> tableView = createTableViewForSectionC(csc200);
                    newLayout.getChildren().removeIf(node -> node instanceof TableView);
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
        List<DatumA> data = course.getSectionA().getData(); 
        TableView<DatumA> tableView = new TableView<>();

        // Create a TableColumn for the "names" property
        TableColumn<DatumA, String> namesColumn = new TableColumn<>("Name");
        namesColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Create a TableColumn for the "Present on" property
        TableColumn<DatumA, List<AttendanceA>> attendanceColumn = new TableColumn<>("Present on");
        attendanceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DatumA, List<AttendanceA>>, ObservableValue<List<AttendanceA>>>() {
            @Override
            public ObservableValue<List<AttendanceA>> call(TableColumn.CellDataFeatures<DatumA, List<AttendanceA>> param) {
                return new SimpleObjectProperty<>(param.getValue().getAttendance());
            }
        });

        // Convert the list of dates to a comma-separated string
        attendanceColumn.setCellFactory(column -> new TextFieldTableCell<>(new StringConverter<>() {
            @Override
            public String toString(List<AttendanceA> object) {
                return object.stream().map(AttendanceA::getDate).collect(Collectors.joining(" "));
            }

            @Override
            public List<AttendanceA> fromString(String string) {
                // Implement if needed
                return null;
            }
        }));

        // Add the columns to the TableView
        tableView.getColumns().addAll(namesColumn,attendanceColumn);

        // Set items to the TableView
        tableView.getItems().addAll(data);

        return tableView;
    }
    
    //For Section B
    private static TableView<DatumB> createTableViewForSectionB(Csc200 course) 
    {
        List<DatumB> data = course.getSectionB().getData(); 
        TableView<DatumB> tableView = new TableView<>();

        // Create a TableColumn for the "names" property
        TableColumn<DatumB, String> namesColumn = new TableColumn<>("Name");
        namesColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Create a TableColumn for the "Present on" property
        TableColumn<DatumB, List<AttendanceB>> attendanceColumn = new TableColumn<>("Present on");
        attendanceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DatumB, List<AttendanceB>>, ObservableValue<List<AttendanceB>>>() {
            @Override
            public ObservableValue<List<AttendanceB>> call(TableColumn.CellDataFeatures<DatumB, List<AttendanceB>> param) {
                return new SimpleObjectProperty<>(param.getValue().getAttendance());
            }
        });

        // Convert the list of dates to a comma-separated string
        attendanceColumn.setCellFactory(column -> new TextFieldTableCell<>(new StringConverter<>() {
            @Override
            public String toString(List<AttendanceB> object) {
                return object.stream().map(AttendanceB::getDate).collect(Collectors.joining(" "));
            }

            @Override
            public List<AttendanceB> fromString(String string) {
                // Implement if needed
                return null;
            }
        }));

        // Add the column to the TableView
        tableView.getColumns().addAll(namesColumn,attendanceColumn);

        // Set items to the TableView
        tableView.getItems().addAll(data);

        return tableView;
    }

    //For Section C
    private static TableView<DatumC> createTableViewForSectionC(Csc200 course) 
    {
        List<DatumC> data = course.getSectionC().getData(); 
        TableView<DatumC> tableView = new TableView<>();

        // Create a TableColumn for the "names" property
        TableColumn<DatumC, String> namesColumn = new TableColumn<>("Name");
        namesColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Create a TableColumn for the "Present on" property
        TableColumn<DatumC, List<AttendanceC>> attendanceColumn = new TableColumn<>("Present on");
        attendanceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DatumC, List<AttendanceC>>, ObservableValue<List<AttendanceC>>>() {
            @Override
            public ObservableValue<List<AttendanceC>> call(TableColumn.CellDataFeatures<DatumC, List<AttendanceC>> param) {
                return new SimpleObjectProperty<>(param.getValue().getAttendance());
            }
        });

        // Convert the list of dates to a comma-separated string
        attendanceColumn.setCellFactory(column -> new TextFieldTableCell<>(new StringConverter<>() {
            @Override
            public String toString(List<AttendanceC> object) {
                return object.stream().map(AttendanceC::getDate).collect(Collectors.joining(" "));
            }

            @Override
            public List<AttendanceC> fromString(String string) {
                // Implement if needed
                return null;
            }
        }));

        // Add the column to the TableView
        tableView.getColumns().addAll(namesColumn,attendanceColumn);

        // Set items to the TableView
        tableView.getItems().addAll(data);

        return tableView;
    }
    
}
