package com.example.quizapp_fe.dialogs;

import android.content.Context;
import android.content.DialogInterface;

public class AlertDialog {
    private Context context;

    public AlertDialog(Context context) {
        this.context = context;
    }

    public void show(String title, String message, DialogInterface.OnClickListener confirmListener) {
        new android.app.AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", confirmListener)
                .show();
    }
}