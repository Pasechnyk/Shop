package com.example.shop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.shop.application.HomeApplication;
import com.example.shop.dto.category.CategoryItemDTO;
import com.example.shop.services.ApplicationNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

//    private ImageView ivAvatar;
//    private ImageView ivProfilePicture;
//    private Button btnRegister;

    private static final int IMAGE_PICK_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ApplicationNetwork
                .getInstance()
                .getCategoriesApi()
                .list()
                .enqueue(new Callback<List<CategoryItemDTO>>() {
                    @Override
                    public void onResponse(Call<List<CategoryItemDTO>> call, Response<List<CategoryItemDTO>> response) {
                        if(response.isSuccessful()) {
                            List<CategoryItemDTO> items = response.body();
                            //int count = items.size();
                            //Log.d("---count---", String.valueOf(count));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CategoryItemDTO>> call, Throwable throwable) {
                        Log.e("--problem--", "error server");
                    }
                });

        //ivAvatar = findViewById(R.id.ivAvatar);
        //ivProfilePicture = findViewById(R.id.ivProfilePicture);
        //btnRegister = findViewById(R.id.btnRegister);

        //Server ip
        //http://10.0.2.2:5297/images/1.png
        //String url = "https://content1.rozetka.com.ua/goods/images/big/415679366.jpg";
        //String url = "http://10.0.2.2:5297/images/1.png";

//        Glide.with(HomeApplication.getAppContext())
//                .load(url)
//                .apply(new RequestOptions().override(400))
//                .into(ivAvatar);

//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Register button clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        ivProfilePicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Вибір фото
//                pickImageFromGallery();
//            }
//        });
    }

//    private void pickImageFromGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, IMAGE_PICK_CODE);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
//            ivProfilePicture.setImageURI(data.getData());
//        }
//    }


}
