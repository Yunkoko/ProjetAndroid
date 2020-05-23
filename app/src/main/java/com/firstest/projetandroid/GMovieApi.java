package com.firstest.projetandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GMovieApi {

    @GET("/films")
    Call<List<GMovies>> getMovieResponse();

    @GET("/people")
    Call<List<GMovies>> getPeopleResponse();

    @GET("/species")
    Call<List<GMovies>> getSpeciesResponse();

    @GET("/locations")
    Call<List<GMovies>> getLocationsResponse();

    @GET("/vehicles")
    Call<List<GMovies>> getVehiclesResponse();
}
