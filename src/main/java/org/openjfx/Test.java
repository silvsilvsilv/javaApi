package org.openjfx;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import org.openjfx.search.*;

public class Test {
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = "{ \"csc200\": { \"total_students\": 1, \"data\": { \"Section_B\": [ { \"id\": 26, \"name\": \"Silva, John Leonil C.\", \"attendance\": [ { \"is_present\": true, \"date\": \"2023-09-20\" }, { \"is_present\": true, \"date\": \"2023-09-21\" }, { \"is_present\": true, \"date\": \"2023-09-29\" }, { \"is_present\": true, \"date\": \"2023-10-02\" }, { \"is_present\": true, \"date\": \"2023-10-04\" }, { \"is_present\": true, \"date\": \"2023-10-11\" }, { \"is_present\": true, \"date\": \"2023-11-08\" } ] } ] } } }";

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
    }
}
