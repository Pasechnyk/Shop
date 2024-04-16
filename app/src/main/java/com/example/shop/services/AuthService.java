package com.example.shop.services;

import com.example.shop.auth.LoginRequest;
import com.example.shop.auth.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest request);
}

