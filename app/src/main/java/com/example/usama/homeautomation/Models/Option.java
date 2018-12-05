package com.example.usama.homeautomation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Option {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("label")
    @Expose
    private String label;

    /**
     * No args constructor for use in serialization
     *
     */
    public Option() {
    }

    /**
     *
     * @param value
     * @param label
     */
    public Option(String value, String label) {
        super();
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}