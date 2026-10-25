package com.pynexia.week1.splash;

import android.os.Bundle;
public class MainActivity extends BaseActivity {
    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        showLayout(R.layout.activity_main);
        findViewById(R.id.finish).setOnClickListener(view -> finish());
    }
}
