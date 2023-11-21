package org.openjfx.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;


public class SearchAttendance 
{
    @JsonProperty("is_present")
    private boolean isPresent;

    @JsonProperty("date")
    private String date;

    // getters and setters

    public SearchAttendance()
    {

    }

    @JsonGetter("is_present")
    public boolean isPresent()
    {
        return this.isPresent;
    }

    @JsonSetter("is_present")
    public void setisPresent(boolean isPresent)
    {
        this.isPresent = isPresent;
    }

    @JsonGetter("date")
    public String getDate()
    {
        return date;
    }

    @JsonSetter("date")
    public void setDate(String date)
    {
        this.date = date;
    }
    
}