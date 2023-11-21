package org.openjfx.all;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "section-a",
    "section-b",
    "section-c"
})

public class Csc200 {

    @JsonProperty("section-a")
    private SectionA sectionA;
    @JsonProperty("section-b")
    private SectionB sectionB;
    @JsonProperty("section-c")
    private SectionC sectionC;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Csc200() {
    }

    /**
     * 
     * @param sectionA
     * @param sectionB
     * @param sectionC
     */
    
    // public Csc200(SectionA sectionA, SectionB sectionB, SectionC sectionC) {
    //     super();
    //     this.sectionA = sectionA;
    //     this.sectionB = sectionB;
    //     this.sectionC = sectionC;
    // }

    @JsonGetter("section-a")
    public SectionA getSectionA() {
        return sectionA;
    }

    @JsonSetter("section-a")
    public void setSectionA(SectionA sectionA) {
        this.sectionA = sectionA;
    }

    @JsonGetter("section-b")
    public SectionB getSectionB() {
        return sectionB;
    }

    @JsonSetter("section-b")
    public void setSectionB(SectionB sectionB) {
        this.sectionB = sectionB;
    }

    @JsonGetter("section-c")
    public SectionC getSectionC() {
        return sectionC;
    }

    @JsonSetter("section-c")
    public void setSectionC(SectionC sectionC) {
        this.sectionC = sectionC;
    }

}
