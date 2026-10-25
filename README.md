# Splash Screen

A native Android introduction that animates a project logo, waits for 2.2 seconds of foreground time, and opens a welcome screen.

Pynexia Android training / Week 1. Java 17 + XML.

[Hosted showcase](https://engeyad.github.io/pynexia-week1-splash-screen/) · [Report PDF](https://engeyad.github.io/pynexia-week1-splash-screen/downloads/Splash-Screen-App-Report.pdf) · [APK](https://engeyad.github.io/pynexia-week1-splash-screen/downloads/Splash-Screen-App.apk) · [Submission ZIP](https://engeyad.github.io/pynexia-week1-splash-screen/downloads/Splash-Screen-App.zip)

## Run

Open this repository root in Android Studio. Install SDK 35 / Build Tools 35.0.0; use JDK 17 or 21. Sync Gradle and run `splash` on Android 8.0 / API 26 or later. Initial sync needs internet. AGP 8.9.1; Gradle Wrapper 8.11.1.

```powershell
.\gradlew.bat :splash:assembleDebug :splash:testDebugUnitTest :splash:lintDebug
.\gradlew.bat :splash:connectedDebugAndroidTest
```

The second command needs an emulator/device. On macOS/Linux, use `chmod +x gradlew` then `./gradlew`. APK output: `splash/build/outputs/apk/debug/splash-debug.apk`.

## Implementation

### Logo and animation

XML layout with an original project mark and a 700 ms fade/scale animation.

### Delay and lifecycle

Handler schedules navigation; onPause removes callbacks and retains the remaining delay. onResume resumes it; onDestroy cleans up.

### Intent navigation

An explicit Intent opens MainActivity. Finishing the splash prevents Back from replaying it.

## Review walkthrough

1. Launch the app and watch the logo animate.
2. After approximately 2.2 seconds in the foreground, confirm the welcome screen appears.
3. Relaunch, background the app during the introduction, then return. The remaining delay resumes.
4. Press Back from the welcome screen: the introduction should not reappear.

## Verification

0 JVM tests and 2 device tests passed, with zero failures, errors or skips. Lint: 0 errors and 7 warnings, without suppression. Two device tests cover automatic navigation, disposal of the introductory Activity, and pausing navigation while backgrounded. There are no separate JVM tests for this UI-only exercise.

Tested 25 October 2026 on a Pixel 8 emulator, Android 17 / API 37 (37.1 image), x86_64. No physical-device or API 26 run. See `verification/` for executed reports; local filesystem prefixes are redacted. Debug APK signature verified.

## Scope

Android 12+ also shows its system launch splash before this assignment's introductory Activity. The logo is a learning-project mark, not an official Pynexia asset.

APKs are debug-signed builds for assessment. The hosted page is a project showcase with actual screenshots and downloads, not an in-browser Android runtime. Publication does not submit the assignment to Pynexia.

## شرح عربي

يعرض التمرين شاشة افتتاحية تحتوي على شعار وحركة بسيطة، ثم ينتقل تلقائيًا إلى الشاشة الرئيسية. يتوقف عدّاد الانتظار عند خروج التطبيق إلى الخلفية ويستكمل المدة المتبقية عند العودة. تنتهي الشاشة الافتتاحية بعد الانتقال حتى لا تظهر مجددًا عند الضغط على زر الرجوع.

للتجربة: افتح التطبيق وانتظر الانتقال التلقائي، ثم أعد تشغيله واخرج إلى الخلفية قبل انتهاء المقدمة وعد إليه للتحقق من استكمال الانتظار.

## Submission

Copy `SUBMISSION-NOTES.txt` into the notes field. Repository URL: https://github.com/ENGEyad/pynexia-week1-splash-screen

Hosted URL: https://engeyad.github.io/pynexia-week1-splash-screen/

Attach the ZIP or PDF. The APK is inside the ZIP, since the supplied form does not accept direct APK attachments.

Reference: https://developer.android.com/guide/components/activities/activity-lifecycle
