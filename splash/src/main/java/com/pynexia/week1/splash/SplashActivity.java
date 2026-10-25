package com.pynexia.week1.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;

public class SplashActivity extends BaseActivity {
    private static final long DELAY_MS = 2200;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private long remaining = DELAY_MS;
    private long startedAt;
    private boolean navigated;
    private final Runnable navigate = () -> {
        if (!navigated && !isFinishing()) {
            navigated = true;
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    };

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        if (state != null) remaining = state.getLong("remaining", DELAY_MS);
        showLayout(R.layout.activity_splash);
        View logo = findViewById(R.id.logo);
        logo.setAlpha(0f);
        logo.setScaleX(0.85f);
        logo.setScaleY(0.85f);
        logo.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(700).start();
    }

    @Override protected void onResume() {
        super.onResume();
        startedAt = SystemClock.elapsedRealtime();
        handler.postDelayed(navigate, remaining);
    }

    @Override protected void onPause() {
        handler.removeCallbacks(navigate);
        remaining = Math.max(0, remaining - (SystemClock.elapsedRealtime() - startedAt));
        super.onPause();
    }

    @Override protected void onSaveInstanceState(Bundle state) {
        state.putLong("remaining", remaining);
        super.onSaveInstanceState(state);
    }

    @Override protected void onDestroy() {
        handler.removeCallbacks(navigate);
        super.onDestroy();
    }
}
