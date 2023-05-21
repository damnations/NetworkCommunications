package com.example.networkcommunications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public final String faceImageLink = "https://loremflickr.com/150/150/face";
    public final String pizzaImageLink = "https://loremflickr.com/150/150/pizza";
    public final String furnitureImageLink = "https://loremflickr.com/150/150/furniture";

    private ArrayList<ImageList> imageList = new ArrayList<>();
    private AlertDialog alertDialog = null;

    private Button addButton;
    private RecyclerView recyclerView;
    private NetworkCommunicationsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Map each UI element to a field variable
        addButton = findViewById(R.id.getPictureButton);
        recyclerView = findViewById(R.id.pictureListRecyclerView);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NetworkCommunicationsRecyclerViewAdapter(this, imageList);
        recyclerView.setAdapter(adapter);

        // Set up addButton click listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View views) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.picture_choice_dialog, null);

                builder.setView(view);

                Button faceImage = view.findViewById(R.id.faceButton);
                Button pizzaImage = view.findViewById(R.id.pizzaButton);
                Button furnitureImage = view.findViewById(R.id.furnitureButton);

                faceImage.setOnClickListener((View v) ->{
                    getPictures(faceImageLink);
                });
                pizzaImage.setOnClickListener((View v) ->{
                    getPictures(pizzaImageLink);
                });
                furnitureImage.setOnClickListener((View v) ->{
                    getPictures(furnitureImageLink);
                });

                alertDialog = builder.create();
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        });
    }

    private void getPictures(String link){
        new Thread(() -> {
            try {
                URL url = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmapImage = BitmapFactory.decodeStream(inputStream);

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        imageList.add(new ImageList(bitmapImage));
                        Log.d("LIST", "run: "+ imageList);
                        adapter.notifyDataSetChanged();

                        if(alertDialog != null){
                            alertDialog.dismiss();
                        }
                    }
                });
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }

        }).start();
    }
}