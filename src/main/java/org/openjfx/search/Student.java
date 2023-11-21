package org.openjfx.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;


public class Student 
{
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("attendance")
    private List<SearchAttendance> attendance;
    
    
    public Student()
    {

    }

    //getters and setters

    @JsonGetter("id")
    public int getId()
    {
        return this.id;
    }

    @JsonSetter("id")
    public void setisPresent(int id)
    {
        this.id = id;
    }

    @JsonGetter("name")
    public String getName()
    {
        return this.name;
    }

    @JsonSetter("name")
    public void setName(String name)
    {
        this.name = name;
    }

    @JsonGetter("attendance")
    public List<SearchAttendance> getAttendance()
    {
        return this.attendance;
    }

    @JsonSetter("attendance")
    public void setAttendance(List<SearchAttendance> attendance)
    {
        this.attendance = attendance;
    }
}
