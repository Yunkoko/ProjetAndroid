package com.firstest.projetandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeApiCall();
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
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GMovieApi MovieApi = retrofit.create(GMovieApi.class);

        Call<List<GMovies>> call = MovieApi.getMovieResponse();
        call.enqueue(new Callback<List<GMovies>>() {
            @Override
            public void onResponse(Call<List<GMovies>> call, Response<List<GMovies>> response) {
                if(response.isSuccessful() && response.body() != null) {
                    List<GMovies> GMovieList = response.body();
                    showList(GMovieList);
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

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}