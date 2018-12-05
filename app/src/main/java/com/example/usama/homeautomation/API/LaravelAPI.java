package com.example.usama.homeautomation.API;

import com.example.usama.homeautomation.Models.Floor;

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
}
