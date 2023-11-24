package org.openjfx.all;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "is_present",
    "date"
})

public class AttendanceB {

    @JsonProperty("is_present")
    private boolean isPresent;
    @JsonProperty("date")
    private String date;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AttendanceB() 
    {
    }

    /**
     * 
     * @param date
     * @param isPresent
     */
    
    // public Attendance__1(boolean isPresent, String date) {
    //     super();
    //     this.isPresent = isPresent;
    //     this.date = date;
    // }

    @JsonGetter("is_present")
    public boolean isIsPresent() {
        return isPresent;
    }

    @JsonSetter("is_present")
    public void setIsPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }

    @JsonGetter("date")
    public String getDate() {
        return date;
    }

    @JsonSetter("date")
    public void setDate(String date) {
        this.date = date;
    }

}
