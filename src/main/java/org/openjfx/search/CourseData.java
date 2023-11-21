package org.openjfx.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class CourseData 
{
    @JsonProperty("total_students")
    private int totalStudents;

    @JsonProperty("data")
    private Map<String, List<Student>> data;

    public CourseData()
    {

    }

    //getters and setters

    @JsonGetter("total_students")
    public int getTotalStudents()
    {
        return this.totalStudents;
    }

    @JsonSetter("total_students")
    public void setTotalStudents(int totalStudents)
    {
        this.totalStudents = totalStudents;
    }

    @JsonGetter("data")
    public Map<String,List<Student>> getData()
    {
        return this.data;
    }

    @JsonSetter("data")
    public void setData(Map<String, List<Student>> data)
    {
        this.data = data;
    }

    
}
