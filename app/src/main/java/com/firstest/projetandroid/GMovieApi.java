package com.firstest.projetandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GMovieApi {

    @GET("/films")
    Call<List<GMovies>> getMovieResponse();
}
