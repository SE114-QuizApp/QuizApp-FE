package com.example.quizapp_fe.models;

import android.content.Context;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MediaMangerObject {
    boolean isInitialized = false;

    public void init(Context context) {
        if (!isInitialized) {
            Logger.getLogger("MediaManagerObject").info("Initializing MediaManager");
            Map config = new HashMap();
            config.put("cloud_name", "dfoiuc0jw");
            config.put("api_key", "293427824815773");
            config.put("api_secret", "a8h2AFQmgyegqDCbDhDiQESkuQg");
            config.put("secure", true);
            MediaManager.init(context, config);
            isInitialized = true;
        }
    }
}
