package com.example.usama.homeautomation.API;

import com.example.usama.homeautomation.Models.Floor;
import com.example.usama.homeautomation.Models.Room;
import com.example.usama.homeautomation.Models.Thing;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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
    Call<Floor> addFloors(
                          @Field("fName") String name);

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

    @GET("rooms/ByfloorId/{floorId}")
    Call<ArrayList<Room>> getRoomByFloor(@Path("floorId") String floorId);

    @POST("rooms")
    @FormUrlEncoded
    Call<Room> addRooms(
                        @Field("rName") String name,
                        @Field("FloorId") int floorID);

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
    Call<ArrayList<Thing> >getThingByRoom(@Path("id") int roomId);

    @POST("things")
    @FormUrlEncoded
    Call<Thing> addThings(
//            @Body List<Thing> thingList
//            @Field("id") String thingID,
                          @Field("tName") String name,
                          @Field("RoomId") String roomID
    );

    @PUT("things/{id}")
    @FormUrlEncoded
    Call<Floor> updateThing(@Path("id") String thingID,
                            @Field("tName") String name,
                            @Field("RoomId") String roomID);

    @DELETE("things/{id}")
    Call<String> deleteThing(@Path("id") String thingID);
}
