package com.avichai98.preferencenews.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.avichai98.preferencenews.R;
import com.avichai98.preferencenews.Utilities.User;

public class OpeningActivity extends AppCompatActivity {
    private User user;
    private AppCompatImageView splash_IMG_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        splash_IMG_icon = findViewById(R.id.splash_IMG_icon);
        animate(splash_IMG_icon);
    }

    private void animate(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        view.setY((float) -displayMetrics.heightPixels / 2 - view.getHeight());
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.setAlpha(0.0f);  // Start with the view being fully transparent

        view.animate()
                .scaleY(1.2f)  // Start by scaling slightly larger than original
                .scaleX(1.2f)
                .alpha(1.0f)  // Gradually fade in
                .translationY(0)
                .setDuration(2500)  // Faster first phase
                .setInterpolator(new OvershootInterpolator())  // Add a bounce effect
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(@NonNull Animator animator) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(@NonNull Animator animator) {
                        startApp();
                    }

                    @Override
                    public void onAnimationCancel(@NonNull Animator animator) { }

                    @Override
                    public void onAnimationRepeat(@NonNull Animator animator) { }
                })
                .start();
    }


    private void startApp() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}