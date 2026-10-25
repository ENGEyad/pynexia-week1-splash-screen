package com.pynexia.week1.splash;
import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.pm.ActivityInfo;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AppFlowTest {
    private void snapshot(String name) throws java.io.IOException {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        android.graphics.Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
        java.io.File folder = new java.io.File(InstrumentationRegistry.getInstrumentation().getTargetContext().getExternalFilesDir(null), "evidence");
        if (!folder.exists() && !folder.mkdirs()) throw new java.io.IOException("Cannot create screenshot folder");
        try (java.io.FileOutputStream stream = new java.io.FileOutputStream(new java.io.File(folder, name + ".png"))) {
            if (!bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, stream)) throw new java.io.IOException("Cannot save screenshot");
        } finally { bitmap.recycle(); }
    }

    private Activity launch(Class<? extends Activity> type) {
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), type);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return InstrumentationRegistry.getInstrumentation().startActivitySync(intent);
    }

    @Test public void automaticallyNavigatesAndFinishesSplash() throws Exception {
        Activity splash = launch(SplashActivity.class);
        Thread.sleep(800);
        snapshot("splash-intro");
        Thread.sleep(2200);
        onView(withText(R.string.welcome)).check(matches(isDisplayed()));
        snapshot("splash-main");
        assertTrue(splash.isFinishing() || splash.isDestroyed());
    }
    @Test public void pausesNavigationInBackground() throws Exception {
        Activity splash = launch(SplashActivity.class);
        Thread.sleep(300);
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(home);
        Thread.sleep(2500);
        assertFalse(splash.isFinishing());
        Intent resume = new Intent(context, SplashActivity.class);
        resume.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(resume);
        Thread.sleep(2600);
        onView(withText(R.string.welcome)).check(matches(isDisplayed()));
    }

}
