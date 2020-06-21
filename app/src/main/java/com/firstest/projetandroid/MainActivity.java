package com.firstest.projetandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MainActivity extends AppCompatActivity implements ListAdapter.OnMovieListener {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ArrayList<String> imagesURL = new ArrayList<String>();
    private ArrayList<GMovies> GMoviesList2 = new ArrayList<GMovies>();
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String BASE_URL = "https://ghibliapi.herokuapp.com/";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private void initImage()
    {
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/thumb/f/f5/Castle_in_the_Sky_%281986%29.png/220px-Castle_in_the_Sky_%281986%29.png");
        imagesURL.add("https://i.redd.it/z2q1b08skk051.jpg");
        imagesURL.add("https://preview.redd.it/ait7s6zdqxu41.jpg?width=716&format=pjpg&auto=webp&s=cd77b4bd682d7c4d5f3160b0d79d5651245415f2");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/0/07/Kiki%27s_Delivery_Service_%28Movie%29.jpg");
        imagesURL.add("https://i.imgur.com/jO3wqTs.jpg");
        imagesURL.add("https://i.redd.it/alrz6zf9ezb41.jpg");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/thumb/6/68/Pompokoposter.jpg/220px-Pompokoposter.jpg");
        imagesURL.add("https://preview.redd.it/umxjkiyxv4y41.jpg?width=716&format=pjpg&auto=webp&s=09f81cf8c7e73b955f4fd5ff4ccbdc61a97150ff");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/thumb/8/8c/Princess_Mononoke_Japanese_poster.png/220px-Princess_Mononoke_Japanese_poster.png");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/thumb/4/4b/My_Neighbors_the_Yamadas_%281999%29.jpg/220px-My_Neighbors_the_Yamadas_%281999%29.jpg");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/thumb/d/db/Spirited_Away_Japanese_poster.png/220px-Spirited_Away_Japanese_poster.png");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/thumb/8/8e/Cat_Returns.jpg/220px-Cat_Returns.jpg");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/a/a0/Howls-moving-castleposter.jpg");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/thumb/e/e5/Gedo6sn.jpg/220px-Gedo6sn.jpg");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/thumb/9/9d/Ponyo_%282008%29.png/220px-Ponyo_%282008%29.png");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/thumb/e/e7/Karigurashi_no_Arrietty_poster.png/220px-Karigurashi_no_Arrietty_poster.png");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/thumb/c/c9/From_Up_on_Poppy_Hill.png/220px-From_Up_on_Poppy_Hill.png");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/a/a3/Kaze_Tachinu_poster.jpg");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/thumb/6/68/The_Tale_of_the_Princess_Kaguya_%28poster%29.jpg/220px-The_Tale_of_the_Princess_Kaguya_%28poster%29.jpg");
        imagesURL.add("https://upload.wikimedia.org/wikipedia/en/thumb/a/a7/When_Marnie_Was_There.png/220px-When_Marnie_Was_There.png");
    }

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
        GMoviesList2 = getArrayDataFromCache();
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

    private ArrayList<GMovies> getArrayDataFromCache() {
        String jsonGmovies = sharedPreferences.getString("jsonGMoviesList", null);

        if(jsonGmovies == null){
            return null;
        } else {
            Type listType = new TypeToken<ArrayList<GMovies>>(){}.getType();
            return gson.fromJson(jsonGmovies, listType);
        }
    }

    private void showList(List<GMovies> GMoviesList) {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        initImage();
        mAdapter = new ListAdapter(GMoviesList, imagesURL, getApplicationContext(), this);
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

    @Override
    public void onMovieClick(int position) {
        Log.d(TAG, "onMovieClick: clicked");
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("image_URL", imagesURL.get(position));
        intent.putExtra( "list", GMoviesList2.get(position));
        startActivity(intent);
    }
}