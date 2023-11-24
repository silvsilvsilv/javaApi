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
    "total_students",
    "data"
})

public class SectionB {

    @JsonProperty("total_students")
    private int totalStudents;
    @JsonProperty("data")
    private List<DatumB> data = new ArrayList<DatumB>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public SectionB() {
    }

    /**
     * 
     * @param data
     * @param totalStudents
     */

    // public SectionB(int totalStudents, List<Datum__1> data) {
    //     super();
    //     this.totalStudents = totalStudents;
    //     this.data = data;
    // }

    @JsonGetter("total_students")
    public int getTotalStudents() {
        return totalStudents;
    }

    @JsonSetter("total_students")
    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    @JsonGetter("data")
    public List<DatumB> getData() {
        return data;
    }

    @JsonSetter("data")
    public void setData(List<DatumB> data) {
        this.data = data;
    }

}
