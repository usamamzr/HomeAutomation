package com.example.usama.homeautomation.API;

import com.example.usama.homeautomation.Models.Floor;
import com.example.usama.homeautomation.Models.Room;
import com.example.usama.homeautomation.Models.Thing;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LaravelAPI {

    /////////////////   FLOORS  //////////////////////

    @GET("floors")
    Call<ArrayList<Floor>> getFloorsList();

    @GET("floors/{id}")
    Call<Floor> getSingleFloor();

    @POST("floors")
    @FormUrlEncoded
    Call<Floor> addFloors(@Field("id") String floorID,
                          @Field("fName") String name,
                          @Field("Icon") String icon);

    @PUT("floors/{id}")
    @FormUrlEncoded
    Call<Floor> updateFloor(@Path("id") String floorID,
                            @Field("fName") String name,
                            @Field("Icon") String icon);

    @DELETE("floors/{id}")
    Call<String> deleteFloor(@Path("id") String floorID);

    //////////////////////////////  ROOMS   //////////////////////////////////

    @GET("rooms")
    Call<ArrayList<Room>> getRoomsList();

    @GET("rooms/{id}")
    Call<Room> getSingleRoom();

    @GET("rooms/ByfloorId/{floorid}")
    Call<Room> getSingleRoomByFloor();

    @POST("rooms")
    @FormUrlEncoded
    Call<Room> addRooms(@Field("id") String roomID,
                         @Field("rName") String name,
                         @Field("Icon") String icon,
                         @Field("FloorId") String floorID);

    @PUT("rooms/{id}")
    @FormUrlEncoded
    Call<Room> updateRoom(@Path("id") String roomID,
                           @Field("rName") String name,
                           @Field("Icon") String icon,
                           @Field("FloorId") String floorID);

    @DELETE("rooms/{id}")
    Call<String> deleteRoom(@Path("id") String roomID);

    //////////////////////////////  THINGS /////////////////////////////////

    @GET("things")
    Call<ArrayList<Thing>> getThingsList();

    @GET("things/{id}")
    Call<Thing> getSingleThing();

    @GET("things/ByroomId/{id}")
    Call<Thing> getSingleThingByRoom();

    @POST("things")
    @FormUrlEncoded
    Call<Thing> addThings(@Field("id") String thingID,
                         @Field("tName") String name,
                         @Field("RoomId") String roomID);

    @PUT("things/{id}")
    @FormUrlEncoded
    Call<Floor> updateThing(@Path("id") String thingID,
                           @Field("tName") String name,
                           @Field("RoomId") String roomID);

    @DELETE("things/{id}")
    Call<String> deleteThing(@Path("id") String thingID);
}
