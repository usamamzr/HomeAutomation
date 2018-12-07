package com.example.usama.homeautomation.API;

import com.example.usama.homeautomation.Models.TblItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OpenhabAPI {

    String BaseURL = "http://192.168.0.136:8080/rest/";

    @GET("items")
    Call<List<TblItem>> getItemList();

    @GET("items/{itemname}")
    Call<TblItem> getItemByName(@Path("itemname") String itemName);

    @GET("items/{itemname}/state")
    Call<String> getItemState(@Path("itemname") String itemState);

    @PUT("item/{itemname}/state")
    @FormUrlEncoded
    Call<Void> setItemState(@Path("itemname") String itemName,
                            @Field("body") String state);


    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
