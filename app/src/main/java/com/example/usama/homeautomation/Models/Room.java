package com.example.usama.homeautomation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Room {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rName")
    @Expose
    private String rName;
    @SerializedName("Icon")
    @Expose
    private String icon;
    @SerializedName("FloorId")
    @Expose
    private String floorId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public Room() {
    }

    /**
     *
     * @param updatedAt
     * @param rName
     * @param id
     * @param icon
     * @param floorId
     * @param createdAt
     */
    public Room(Integer id, String rName, String icon, String floorId, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.rName = rName;
        this.icon = icon;
        this.floorId = floorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Room(String s) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRName() {
        return rName;
    }

    public void setRName(String rName) {
        this.rName = rName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}