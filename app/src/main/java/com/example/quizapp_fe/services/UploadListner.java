package com.example.quizapp_fe.services;

import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.ListenerService;

import java.util.Map;

public class UploadListner extends ListenerService {
    @Override
    public void onStart(String requestId) {
    }

    @Override
    public void onProgress(String requestId, long bytes, long totalBytes) {
    }

    @Override
    public void onSuccess(String requestId, Map resultData) {
    }

    @Override
    public void onError(String requestId, ErrorInfo error) {
    }

    @Override
    public void onReschedule(String requestId, ErrorInfo error) {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
