package com.firstest.projetandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.firstest.projetandroid.GMovieApi.*;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String BASE_URL = "https://ghibliapi.herokuapp.com/";
    private SharedPreferences sharedPreferences;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("app_project", Context.MODE_PRIVATE);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<GMovies> GMoviesList = getDataFromCache();
        if(GMoviesList != null) {
            showList(GMoviesList);
        } else {
            makeApiCall();
        }
    }

    private List<GMovies> getDataFromCache() {
        String jsonGmovies = sharedPreferences.getString("jsonGMoviesList", null);

        if(jsonGmovies == null){
            return null;
        } else {
            Type listType = new TypeToken<List<GMovies>>(){}.getType();
            return gson.fromJson(jsonGmovies, listType);
        }
    }

    private void showList(List<GMovies> GMoviesList) {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        // define an adapter
        mAdapter = new ListAdapter(GMoviesList);
        recyclerView.setAdapter(mAdapter);
    }

    private void makeApiCall() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GMovieApi MovieApi = retrofit.create(GMovieApi.class);

        Call<List<GMovies>> call = MovieApi.getMovieResponse();
        call.enqueue(new Callback<List<GMovies>>() {
            @Override
            public void onResponse(@NonNull Call<List<GMovies>> call, @NonNull Response<List<GMovies>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<GMovies> GMovieList = response.body();
                    showList(GMovieList);
                    savelist(GMovieList);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<GMovies>> call, Throwable t) {
                showError();
            }
        });
    }

    private void savelist(List<GMovies> gMovieList) {
        String jsonString = gson.toJson(gMovieList);
        sharedPreferences
                .edit()
                .putString("jsonGMoviesList", jsonString)
                .apply();

        Toast.makeText(getApplicationContext(), "List saved", Toast.LENGTH_SHORT).show();

    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}