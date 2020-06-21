package com.firstest.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
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
    }

    private void getIncomingIntent()
    {
        if(getIntent().hasExtra("image_URL") && getIntent().hasExtra("title") && getIntent().hasExtra("desc") && getIntent().hasExtra("year") && getIntent().hasExtra("dir") && getIntent().hasExtra("prod"))
        {
            String imageURL = getIntent().getStringExtra("image_URL");
            String title = getIntent().getStringExtra("title");
            String desc = getIntent().getStringExtra("desc");
            int year = getIntent().getIntExtra("year", 0);
            String dir = getIntent().getStringExtra("dir");
            String prod = getIntent().getStringExtra("prod");

            setInfo(imageURL, title, desc, year, dir, prod);
        }
    }

    private void setInfo(String imageURL, String title, String desc, int year, String dir, String prod)
    {
        TextView vTitle = findViewById(R.id.title);
        vTitle.setText(title);

        ImageView image = findViewById(R.id.icon2);
        Glide.with(this)
                .asBitmap()
                .load(imageURL)
                .into(image);

        EditText vDesc = findViewById(R.id.desc);
        vDesc.setText(desc);

        TextView vYear = findViewById(R.id.year);
        vYear.setText(year);

        TextView vDir = findViewById(R.id.dir);
        vDir.setText(dir);

        TextView vProd = findViewById(R.id.prod);
        vProd.setText(prod);
    }
}
