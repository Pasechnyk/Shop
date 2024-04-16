package com.example.shop.network;

import com.example.shop.dto.category.CategoryCreateDTO;
import com.example.shop.dto.category.CategoryItemDTO;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CategoriesApi {
    @GET("/api/categories")
    public Call<List<CategoryItemDTO>> list();

    @POST("/api/categories")
    public Call<CategoryItemDTO> create(@Body CategoryCreateDTO model);

    // Image
    @Multipart
    @POST("categories/uploadImage")
    Call<String> uploadImage(@Part MultipartBody.Part image);
}