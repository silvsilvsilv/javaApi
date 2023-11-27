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

    public SearchAttendance(boolean isPresent, String date)
    {
        this.isPresent = isPresent;
        this.date = date;
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

    // New method to get string representation of isPresent
    public String getIsPresentString() {
        return isPresent() ? "Yes" : "No";
    }

    // New method to set string representation of isPresent
    public void setIsPresentString(String isPresentString) {
        // You may implement this method if needed
    }
    
}