package org.openjfx;


import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.openjfx.search.*;

public class Test  {

   
    public void display() {

        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Get One Student Info");
        // Replace this with your actual JSON data
        String jsonData = "{ \"csc200\": { \"total_students\": 1, \"data\": { \"Section A\": [ { \"id\": 26, \"name\": \"Silva, John Leonil C.\", \"attendance\": [ { \"is_present\": true, \"date\": \"2023-09-20\" }, { \"is_present\": true, \"date\": \"2023-09-21\" }, { \"is_present\": true, \"date\": \"2023-09-29\" }, { \"is_present\": true, \"date\": \"2023-10-02\" }, { \"is_present\": true, \"date\": \"2023-10-04\" }, { \"is_present\": true, \"date\": \"2023-10-11\" }, { \"is_present\": true, \"date\": \"2023-11-08\" } ] } ] } } }";

        // Parse JSON using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Deserialize JSON to CSC200 object
            CSC200 csc200 = objectMapper.readValue(jsonData, CSC200.class);

            // Access the attendance property of a Student object
            Map<String, List<Student>> data = csc200.getCsc200Data().getData();
            List<Student> studentsInSectionA = data.get("Section A");

            if (!studentsInSectionA.isEmpty()) {
                Student firstStudent = studentsInSectionA.get(0);
                List<SearchAttendance> attendanceList = firstStudent.getAttendance();

                // Create TableView
                TableView<SearchAttendance> tableView = new TableView<>();

                // Create columns
                TableColumn<SearchAttendance, String> isPresentColumn = new TableColumn<>("Is Present");
                TableColumn<SearchAttendance, String> dateColumn = new TableColumn<>("Date");

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

                // Create scene and set stage
                Scene scene = new Scene(tableView, 400, 300);
                window.setScene(scene);
                window.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateTableView(TableView<SearchAttendance> tableView, List<SearchAttendance> attendanceList) {
        ObservableList<SearchAttendance> observableAttendanceList = FXCollections.observableArrayList(attendanceList);

        // Convert boolean values to strings for display
        observableAttendanceList.forEach(attendance -> {
            attendance.setIsPresentString(attendance.isPresent() ? "Yes" : "No");
        });

        tableView.setItems(observableAttendanceList);
    }
}
