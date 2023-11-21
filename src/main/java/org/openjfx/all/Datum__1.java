package org.openjfx.all;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "attendance"
})

public class Datum__1 {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("attendance")
    private List<Attendance__1> attendance = new ArrayList<Attendance__1>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum__1() {
    }

    /**
     * 
     * @param name
     * @param id
     * @param attendance
     */

    // public Datum__1(int id, String name, List<Attendance__1> attendance) {
    //     super();
    //     this.id = id;
    //     this.name = name;
    //     this.attendance = attendance;
    // }

    @JsonGetter("id")
    public int getId() {
        return id;
    }

    @JsonSetter("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonGetter("attendance")
    public List<Attendance__1> getAttendance() {
        return attendance;
    }

    @JsonSetter("attendance")
    public void setAttendance(List<Attendance__1> attendance) {
        this.attendance = attendance;
    }

}
