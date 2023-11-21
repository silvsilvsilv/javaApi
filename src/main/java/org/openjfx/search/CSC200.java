package org.openjfx.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;


public class CSC200 
{
    @JsonProperty("csc200")
    private CourseData csc200Data;

    // getters and setters

    @JsonGetter("csc200")
    public CourseData getCsc200Data()
    {
        return this.csc200Data;
    }

    @JsonSetter("csc200")
    public void setCsc200Data(CourseData csc200data)
    {
        this.csc200Data = csc200data;
    }
}
