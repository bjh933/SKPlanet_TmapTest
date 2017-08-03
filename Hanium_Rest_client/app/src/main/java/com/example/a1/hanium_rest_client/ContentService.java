package com.example.a1.hanium_rest_client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by a1 on 2017. 7. 10..
 */

public interface ContentService {

    @GET("/weather")
    Call<Repo> repo(@Query("lon") double lon, @Query("lat") double lat);

}
