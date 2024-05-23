package com.example.quizapp_fe.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ConfirmationDialog {
    private Context context;

    public ConfirmationDialog(Context context) {
        this.context = context;
    }

    public void show(String title, String message, DialogInterface.OnClickListener confirmListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", confirmListener)
                .setNegativeButton("No", null)
                .show();
    }
}