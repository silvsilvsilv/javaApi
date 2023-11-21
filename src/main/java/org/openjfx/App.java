package org.openjfx;

import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.annotation.JsonProperty;
// import com.fasterxml.jackson.annotation.JsonCreator;
// import com.fasterxml.jackson.annotation.JsonGetter;
// import com.fasterxml.jackson.annotation.JsonSetter;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.openjfx.all.*;
import org.openjfx.search.*;

public class App extends Application {

    private static final String API_URL = "https://my-json-server.typicode.com/silvsilvsilv/simpleApi/db";
    

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = "{ \"csc200\": { \"total_students\": 1, \"data\": { \"Section A\": [ { \"id\": 26, \"name\": \"Silva, John Leonil C.\", \"attendance\": [ { \"is_present\": true, \"date\": \"2023-09-20\" }, { \"is_present\": true, \"date\": \"2023-09-21\" }, { \"is_present\": true, \"date\": \"2023-09-29\" }, { \"is_present\": true, \"date\": \"2023-10-02\" }, { \"is_present\": true, \"date\": \"2023-10-04\" }, { \"is_present\": true, \"date\": \"2023-10-11\" }, { \"is_present\": true, \"date\": \"2023-11-08\" } ] } ] } } }";

            CSC200 csc200 = objectMapper.readValue(jsonData, CSC200.class);
            CourseData courseData = csc200.getCsc200Data();
            int totalStudents = courseData.getTotalStudents();
            Map<String, List<Student>> sectionData = courseData.getData();

            // Now you can work with the parsed data dynamically
            System.out.println("Total Students: " + totalStudents);

            for (Map.Entry<String, List<Student>> entry : sectionData.entrySet()) {
                String sectionName = entry.getKey();
                List<Student> students = entry.getValue();

                System.out.println("Section: " + sectionName);

                // Now you can iterate over the students in this section
                for (Student student : students) {
                    System.out.println("Student ID: " + student.getId());
                    System.out.println("Student Name: " + student.getName());
                    // You can access attendance data here
                    List<SearchAttendance> attendanceList = student.getAttendance();
                    for (SearchAttendance attendance : attendanceList) {
                        System.out.println("Date: " + attendance.getDate());
                        System.out.println("Is Present: " + attendance.isPresent());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        // Label id = new Label("Id: ");
        // Label name = new Label("Name: ");
        // Label isPresent = new Label("Is Present: ");
        // Label date = new Label("Date: ");
        Label text = new Label("Total: ");
        Label name = new Label("Name: ");
        VBox root = new VBox(text, name);

        // Make HTTP request, parse JSON, and update UI
        try {
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

            SectionA sectionA = course.getCsc200().getSectionA();
            System.out.println("Total Students in section A: " + sectionA.getTotalStudents());

            String selected = "";

            for(int i = 0 ; i < sectionA.getData().size();i++)
            {
                if(sectionA.getData().get(i).getId() == 1)
                {
                    selected = sectionA.getData().get(i).getName();
                }
            }

            
            // Step 3: Update JavaFX UI
            
            // idLabel.setText("Id: " + data.getId());
            // userIdLabel.setText("User ID: " + data.getUserId());
            // titleLabel.setText("Title: " + data.getTitle());
            // bodyLabel.setText("Body: " + data.getBody());
            text.setText("Total: " + sectionA.getTotalStudents());
            name.setText("Name of Student at Id 1: " + selected);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up the JavaFX scene
        Scene scene = new Scene(root, 400, 200);
        primaryStage.setTitle("REST API Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setRoot(String string) 
    {
    }
}
