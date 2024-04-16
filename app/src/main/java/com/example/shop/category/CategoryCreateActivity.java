package com.example.shop.category;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.shop.BaseActivity;
import com.example.shop.MainActivity;
import com.example.shop.R;
import com.example.shop.dto.category.CategoryCreateDTO;
import com.example.shop.dto.category.CategoryItemDTO;
import com.example.shop.services.ApplicationNetwork;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryCreateActivity extends BaseActivity {

    TextInputLayout tlCategoryName;
    TextInputLayout tlCategoryDescription;

    // Змінні зображення
    private ImageView ivCategoryImage;
    private Uri selectedImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);

        tlCategoryName = findViewById(R.id.tlCategoryName);
        tlCategoryDescription = findViewById(R.id.tlCategoryDescription);
        ivCategoryImage = findViewById(R.id.ivCategoryImage);
    }
    public void onClickCreateCategory(View view) {
        try {
            String name = tlCategoryName.getEditText().getText().toString().trim();
            String description = tlCategoryDescription.getEditText().getText().toString().trim();
            CategoryCreateDTO dto = new CategoryCreateDTO();
            dto.setName(name);
            dto.setDescription(description);
            ApplicationNetwork.getInstance()
                    .getCategoriesApi()
                    .create(dto)
                    .enqueue(new Callback<CategoryItemDTO>() {
                        @Override
                        public void onResponse(Call<CategoryItemDTO> call, Response<CategoryItemDTO> response) {
                            if(response.isSuccessful())
                            {
                                Intent intent = new Intent(CategoryCreateActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<CategoryItemDTO> call, Throwable throwable) {

                        }
                    });

            if (selectedImageUri != null) {
                uploadImage(selectedImageUri);
            }

        }
        catch(Exception ex) {
            Log.e("--CategoryCreateActivity--", "Problem create "+ ex.getMessage());
        }

    }

    // Вибір зображення
    public void onClickSelectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivity(intent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            ivCategoryImage.setImageURI(selectedImageUri);
        }
    }

    // Завантаження зображення
    private void uploadImage(Uri imageUri) {
        File file = new File(imageUri.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

        ApplicationNetwork.getInstance()
                .getCategoriesApi()
                .uploadImage(imagePart)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            // Handle successful image upload
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        Log.e("--CategoryCreateActivity--", "Failed to upload image: " + throwable.getMessage());
                    }
                });
    }
}