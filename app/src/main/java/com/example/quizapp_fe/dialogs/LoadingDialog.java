package com.example.quizapp_fe.dialogs;

import static com.example.quizapp_fe.R.color.transparent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.quizapp_fe.R;
import com.example.quizapp_fe.databinding.DialogLoadingBinding;

import java.util.Objects;

public class LoadingDialog extends Dialog {
    DialogLoadingBinding binding;
    Context context;
    public LoadingDialog(@NonNull Context context) {
        super(context);
        this.setCancelable(false);
        this.context = context;
        binding = DialogLoadingBinding.inflate(((Activity) context).getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the dialog to appear in the center of the screen
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(Objects.requireNonNull(getWindow()).getAttributes());
        // Set the width of the dialog to 80% of the screen width
        layoutParams.width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        // Apply the layout parameters to the dialog
        getWindow().setAttributes(layoutParams);
        getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(transparent, context.getTheme())));
    }


    public void showLoading(String text){
        if(isShowing()) return;
        this.setCancelable(false);
        binding.loadingDialogProgress.setVisibility(View.VISIBLE);
        binding.loadingDialogMessage.setText(text);
        show();
    }

    public void showLoading(){
        if(isShowing()) return;
        this.setCancelable(false);
        binding.loadingDialogProgress.setVisibility(View.VISIBLE);
        binding.loadingDialogMessage.setText(R.string.loading);
        show();
    }

    public void showError(String error){
        if(isShowing()) dismiss();
        this.setCancelable(true);
        binding.getRoot().removeView(binding.loadingDialogProgress);
        binding.loadingDialogMessage.setText(error);
        binding.loadingDialogCloseBtn.setVisibility(View.VISIBLE);
        binding.loadingDialogCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        show();
    }

    public void showError(){
        if(isShowing()) dismiss();
        this.setCancelable(true);
        binding.getRoot().removeView(binding.loadingDialogProgress);
        binding.loadingDialogMessage.setText(R.string.an_error_occurred_please_try_again_later);
        binding.loadingDialogCloseBtn.setVisibility(View.VISIBLE);
        binding.loadingDialogCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        show();
    }

    public void showError(String error, View.OnClickListener onClickListener){
        if(isShowing()) dismiss();
        this.setCancelable(true);
        binding.getRoot().removeView(binding.loadingDialogProgress);
        binding.loadingDialogMessage.setText(error);
        binding.loadingDialogCloseBtn.setVisibility(View.VISIBLE);
        binding.loadingDialogCloseBtn.setOnClickListener(onClickListener);
        show();
    }
}
