package com.example.sr_wedding;

public interface ResponseCallback {
    void onResponse(String response);
    void onError(Throwable throwable);
}