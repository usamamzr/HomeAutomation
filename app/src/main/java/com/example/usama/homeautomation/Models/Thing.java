package com.example.usama.homeautomation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thing {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tName")
    @Expose
    private String tName;
    @SerializedName("RoomId")
    @Expose
    private String roomId;
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
    public Thing() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param tName
     * @param createdAt
     * @param roomId
     */
    public Thing(Integer id, String tName, String roomId, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.tName = tName;
        this.roomId = roomId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTName() {
        return tName;
    }

    public void setTName(String tName) {
        this.tName = tName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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