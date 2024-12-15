package com.example.sr_wedding.network;

import com.example.sr_wedding.model.ThemeRequest;
import com.example.sr_wedding.model.ThemeResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GeminiApi {

    @Headers("Authorization: AIzaSyDQfuSj-R7wmULXHh88lFMGoeM9J6KlOCw") // Replace with your API key
    @POST("https://generativelanguage.googleapis.com/v1/theme-suggestions") // Replace with the actual endpoint for theme suggestions
    Call<ThemeResponse> getThemeSuggestions(@Body ThemeRequest request);
}
