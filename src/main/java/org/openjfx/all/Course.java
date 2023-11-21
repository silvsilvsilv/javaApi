package org.openjfx.all;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "csc200"
})

public class Course {

    @JsonProperty("csc200")
    private Csc200 csc200;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Course() 
    {
    }

    // /**
    //  * 
    //  * @param csc200
    //  */
    // public Course(Csc200 csc200) {
    //     super();
    //     this.csc200 = csc200;
    // }

    @JsonGetter("csc200")
    public Csc200 getCsc200() {
        return csc200;
    }

    @JsonSetter("csc200")
    public void setCsc200(Csc200 csc200) {
        this.csc200 = csc200;
    }

}
