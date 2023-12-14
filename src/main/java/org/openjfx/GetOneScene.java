package org.openjfx;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.openjfx.search.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GetOneScene 
{
    //API Link, just concatenante with "student"
    private static String API_URL = "https://serval-select-totally.ngrok-free.app/api/student/search?search=";
    
    //Json Data as String for Testing
    private static String jsonData = "{ \"csc200\": { \"total_students\": 1, \"data\": { \"Section A\": [ { \"id\": 26, \"name\": \"Silva, John Leonil C.\", \"attendance\": [ { \"is_present\": true, \"date\": \"2023-09-20\" }, { \"is_present\": true, \"date\": \"2023-09-21\" }, { \"is_present\": true, \"date\": \"2023-09-29\" }, { \"is_present\": true, \"date\": \"2023-10-02\" }, { \"is_present\": true, \"date\": \"2023-10-04\" }, { \"is_present\": true, \"date\": \"2023-10-11\" }, { \"is_present\": true, \"date\": \"2023-11-08\" } ] } ] } } }";
    private static String a = "https://my-json-server.typicode.com/silvsilvsilv/simpleApi/db";

    public static CSC200 APIRequest(String student)
    {
        try
        {   
            //Request a specific student by their name
            //...api/student/search?search="student"

            String newURL = API_URL + student;

            //Step 1: Make HTTP request
            URL url = new URL(a);
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

            CSC200 csc200 = objectMapper.readValue(jsonData, CSC200.class);


            return csc200;

        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        return null;
    }

    public void display()
    {
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Get One Student Info");

        Label label = new Label();
        label.setText("Student to Search: ");
        Label studentLabel = new Label();
        studentLabel.setPadding(new Insets(10,0,10,0));

        // Create a TextField
        TextField textField = new TextField();
        textField.setMaxWidth(200);

        // Create a variable to store the text from the TextField
        StringBuilder userInput = new StringBuilder();

        // Add an event listener to the TextField to capture the input
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Update the variable with the new input
            userInput.setLength(0); // Clear the StringBuilder
            userInput.append(newValue);
        });
        

        // Create a Button
        Button showTableButton = new Button("GET");

        //Data from the API Request
        CSC200 csc200 = APIRequest(userInput.toString().toLowerCase());

        //For Student section container
        GetOneScene outer = new GetOneScene();
        MutableContainer<String> studSection = outer.new MutableContainer<>("");

        // Iterate through all sections
        Map<String, List<Student>> dataMap = csc200.getCsc200Data().getData();

        for (Map.Entry<String, List<Student>> entry : dataMap.entrySet()) {
            // System.out.println("Section: " + entry.getKey());
            studSection.setValue(entry.getKey());

            // Assuming there is only one student in each section
            List<Student> students = entry.getValue();
            for (Student stud : students) {
                // Print dates for the student's attendance
                List<SearchAttendance> attendanceList = stud.getAttendance();
                for (SearchAttendance attendance : attendanceList) {
                    // System.out.println("Date: " + attendance.getDate());
                }
            }
        }

        //Adds padding between textfield and label
        VBox labelLayout = new VBox();
        labelLayout.setPadding(new Insets(0,0,10,0));
        labelLayout.getChildren().addAll(label,textField);
        
        // Create a layout and add the TextField to it
        VBox root = new VBox(labelLayout,showTableButton,studentLabel);
        root.setPadding(new Insets(20,20,20,20));
        root.setAlignment(Pos.TOP_LEFT);

        VBox newLayout = new VBox(root);

        // Access the attendance property of a Student object
        Map<String, List<Student>> data = csc200.getCsc200Data().getData();
        String key = studSection.getValue();
        
        List<Student> studentsInSection = data.get(key);
        

        // Add an event handler to the button to output the user input
        showTableButton.setOnAction(event -> {
            
            studentLabel.setText("Student: " + userInput.toString() + "\nSection: " + studSection.getValue());
            // System.out.println((studentsInSection == null)? "It is Null":"No It is not Null");
            if (studentsInSection != null) 
            {   Student firstStudent = studentsInSection.get(0);
                List<SearchAttendance> attendanceList = firstStudent.getAttendance();

                // Create TableView
                TableView<SearchAttendance> tableView = new TableView<>();

                // Create columns
                TableColumn<SearchAttendance, String> isPresentColumn = new TableColumn<>("Is Present");
                TableColumn<SearchAttendance, String> dateColumn = new TableColumn<>("Date");

                //Set column width
                isPresentColumn.setMinWidth(300);
                dateColumn.setMinWidth(300);

                // Set cell value factories
                isPresentColumn.setCellValueFactory(cellData -> {
                    boolean isPresent = cellData.getValue().isPresent();
                    return new SimpleStringProperty(String.valueOf(isPresent ? "Yes" : "No"));
                });
                dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));

                // Add columns to the table
                tableView.getColumns().addAll(isPresentColumn, dateColumn);

                // Populate TableView with attendance data
                populateTableView(tableView, attendanceList);
                newLayout.getChildren().removeIf(node -> node instanceof TableView);
                newLayout.getChildren().add(tableView);
            }
        });


        // Create a scene and set it on the stage
        Scene scene = new Scene(newLayout, 601, 601);
        window.setScene(scene);

        // Set the stage title and show it
        window.show();

    }

    private void populateTableView(TableView<SearchAttendance> tableView, List<SearchAttendance> attendanceList) {
        ObservableList<SearchAttendance> observableAttendanceList = FXCollections.observableArrayList(attendanceList);

        // Convert boolean values to strings for display
        observableAttendanceList.forEach(attendance -> {
            attendance.setIsPresentString(attendance.isPresent() ? "Yes" : "No");
        });

        tableView.setItems(observableAttendanceList);
    }
    public class MutableContainer<T> 
    {
        private T value;

        public MutableContainer() {
        }

        public MutableContainer(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}
