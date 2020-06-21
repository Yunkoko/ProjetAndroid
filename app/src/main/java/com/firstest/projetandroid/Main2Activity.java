package com.firstest.projetandroid;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity {

    private static final String TAG = "Main2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d(TAG, "oncreated started");

        getIncomingIntent();
    }

    private void getIncomingIntent()
    {
        if(getIntent().hasExtra("image_URL") && getIntent().hasExtra("list"))
        {
            String imageURL = getIntent().getStringExtra("image_URL");
            GMovies movie =  getIntent().getParcelableExtra("list");
            Log.d(TAG, "Oncreate : " + movie.getTitle());
            setInfo(imageURL, movie);
        }
    }

    private void setInfo(String imageURL, GMovies movie)
    {
        TextView vTitle = findViewById(R.id.title);
        vTitle.setText(movie.getTitle());

        ImageView image = findViewById(R.id.icon2);
        Glide.with(this)
                .asBitmap()
                .load(imageURL)
                .into(image);

        TextView vDesc = findViewById(R.id.desc);
        vDesc.setText(movie.getDescription());

        //TextView vYear = findViewById(R.id.year);
        //vYear.setText(movie.getRelease_date());

        TextView vDir = findViewById(R.id.dir);
        vDir.setText(movie.getDirector());

        TextView vProd = findViewById(R.id.prod);
        vProd.setText(movie.getProducer());
    }
}
