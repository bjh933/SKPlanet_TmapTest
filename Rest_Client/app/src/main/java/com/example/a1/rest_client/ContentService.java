package com.example.a1.rest_client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by a1 on 2017. 7. 10..
 */

public interface ContentService {

    @GET("/data/2.5/weather")
    Call<Repo> repo(@Query("appid") String appid, @Query("lat") double lat, @Query("lon") double lon);

}
