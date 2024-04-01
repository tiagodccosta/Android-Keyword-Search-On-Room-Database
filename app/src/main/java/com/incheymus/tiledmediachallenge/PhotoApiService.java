package com.incheymus.tiledmediachallenge;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PhotoApiService {
    @GET("image_objects.json")
    Call<List<Photo>> getPhotos(
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );
}
