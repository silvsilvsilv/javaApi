package org.openjfx;

//dependencies
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.*;

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
    // private static final String api = "https://pastebin.com/raw/TytE0smz?fbclid=IwAR25BvK84-yTiBeIpdgXX3KcfbOuWB9pmE0NRqyEzkaypU9ycVGbJYFYkes";
    private static final String api = "http://127.0.0.1:8000/api/all";

    //Handles the APIRequest and returns a class CSC200
    public static Csc200 APIRequest()
    {
        try
        {
            // Step 1: Make HTTP request
            URL url = new URL(api);
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

            // Convert StringBuilder to String
            String responseString = response.toString();

            // Step 2: Parse JSON with Jackson
            ObjectMapper objectMapper = new ObjectMapper();

            Course course = objectMapper.readValue(responseString, Course.class);

            Csc200 csc200 = course.getCsc200();

            return csc200;

        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        return null;
    }
    
    //For GUI
    public static void display()
    {
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Get All Student Info");

        //Font
        Font.loadFont(GetAllScene.class.getResourceAsStream("C:\\Users\\ASUS\\Downloads\\FiraCode\\FiraCodeNerdFont-Medium.ttf"), 16);
        // Apply the custom font globally to all components
        Font customFont = Font.font("FiraCodeNerdFont-Medium", 16);
        
        //For Menu Controls i.e. choosing section and the button to "GET"
        Label label = new Label();
        label.setFont(customFont);
        label.setText("Sections: ");
        Label numOfStudents = new Label();
        numOfStudents.setFont(customFont);
        numOfStudents.setPadding(new Insets(10,0,10,0));
        Button showTableButton = new Button("GET");
        
        //For the Choices of user
        ChoiceBox<String> sectionChoices = new ChoiceBox<>();
        
        sectionChoices.getItems().addAll("Section A", "Section B", "Section C");
        sectionChoices.setValue("Section A");

        Csc200 csc200 = APIRequest();

        //For Layout of the "label" and sectionChoices choicebox
        VBox layout = new VBox();
        layout.setPadding(new Insets(0,0,10,0));
        layout.getChildren().addAll(label,sectionChoices);

        VBox newLayout = new VBox();
        newLayout.getChildren().addAll(layout,showTableButton,numOfStudents);
        newLayout.setPadding(new Insets(20,20,20,20));
        newLayout.setAlignment(Pos.TOP_LEFT);

        //Adds the table when user presses "GET" button
        showTableButton.setOnAction(event -> {
            String selectedSection = sectionChoices.getValue();
            if (selectedSection != null) 
            {
                if(selectedSection == "Section A")
                {
                    numOfStudents.setText("Number of Students: " + csc200.getSectionA().getTotalStudents());
                    TableView<DatumA> tableView = createTableViewForSectionA(csc200);

                    // Clear existing children before adding the new TableView
                    newLayout.getChildren().removeIf(node -> node instanceof TableView);
                    newLayout.getChildren().add(tableView);
                }
                else if(selectedSection == "Section B")
                {
                    numOfStudents.setText("Number of Students: " + csc200.getSectionB().getTotalStudents());

                    TableView<DatumB> tableView = createTableViewForSectionB(csc200);
                    newLayout.getChildren().removeIf(node -> node instanceof TableView);
                    newLayout.getChildren().add(tableView);
                }
                else if(selectedSection == "Section C")
                {
                    numOfStudents.setText("Number of Students: " + csc200.getSectionC().getTotalStudents());

                    TableView<DatumC> tableView = createTableViewForSectionC(csc200);
                    newLayout.getChildren().removeIf(node -> node instanceof TableView);
                    newLayout.getChildren().add(tableView);
                }
                
            }
        });


        Scene scene = new Scene(newLayout,641,641);
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
        namesColumn.setMinWidth(200);

        // Create a TableColumn for the "Present on" property
        TableColumn<DatumA, List<AttendanceA>> attendanceColumn = new TableColumn<>("Present on");
        attendanceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DatumA, List<AttendanceA>>, ObservableValue<List<AttendanceA>>>() {
            @Override
            public ObservableValue<List<AttendanceA>> call(TableColumn.CellDataFeatures<DatumA, List<AttendanceA>> param) {
                return new SimpleObjectProperty<>(param.getValue().getAttendance());
            }
        });

        attendanceColumn.setMinWidth(400);

        // Convert the list of dates to a \n-separated string
        attendanceColumn.setCellFactory(column -> new TextFieldTableCell<>(new StringConverter<>() {
            @Override
            public String toString(List<AttendanceA> object) {
                return object.stream().map(AttendanceA::getDate).collect(Collectors.joining("\n"));
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
        namesColumn.setMinWidth(200);

        // Create a TableColumn for the "Present on" property
        TableColumn<DatumB, List<AttendanceB>> attendanceColumn = new TableColumn<>("Present on");
        attendanceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DatumB, List<AttendanceB>>, ObservableValue<List<AttendanceB>>>() {
            @Override
            public ObservableValue<List<AttendanceB>> call(TableColumn.CellDataFeatures<DatumB, List<AttendanceB>> param) {
                return new SimpleObjectProperty<>(param.getValue().getAttendance());
            }
        });
        attendanceColumn.setMinWidth(400);

        // Convert the list of dates to a comma-separated string
        attendanceColumn.setCellFactory(column -> new TextFieldTableCell<>(new StringConverter<>() {
            @Override
            public String toString(List<AttendanceB> object) {
                return object.stream().map(AttendanceB::getDate).collect(Collectors.joining("\n"));
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
        namesColumn.setMinWidth(200);

        // Create a TableColumn for the "Present on" property
        TableColumn<DatumC, List<AttendanceC>> attendanceColumn = new TableColumn<>("Present on");
        attendanceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DatumC, List<AttendanceC>>, ObservableValue<List<AttendanceC>>>() {
            @Override
            public ObservableValue<List<AttendanceC>> call(TableColumn.CellDataFeatures<DatumC, List<AttendanceC>> param) {
                return new SimpleObjectProperty<>(param.getValue().getAttendance());
            }
        });
        attendanceColumn.setMinWidth(400);

        // Convert the list of dates to a comma-separated string
        attendanceColumn.setCellFactory(column -> new TextFieldTableCell<>(new StringConverter<>() {
            @Override
            public String toString(List<AttendanceC> object) {
                return object.stream().map(AttendanceC::getDate).collect(Collectors.joining("\n"));
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
