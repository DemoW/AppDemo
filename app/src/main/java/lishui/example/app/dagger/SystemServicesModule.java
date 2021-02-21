/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lishui.example.app.dagger;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.role.RoleManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutManager;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.os.UserManager;
import android.os.Vibrator;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;

import androidx.annotation.Nullable;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lishui.example.app.dagger.qualifiers.Main;

/**
 * Provides Non-AppDemo, Framework-Owned instances to the dependency graph.
 */
@Module
public class SystemServicesModule {
    @Provides
    @Singleton
    static AccessibilityManager provideAccessibilityManager(Context context) {
        return context.getSystemService(AccessibilityManager.class);
    }

    @Provides
    @Singleton
    static ActivityManager provideActivityManager(Context context) {
        return context.getSystemService(ActivityManager.class);
    }

    @Singleton
    @Provides
    static AlarmManager provideAlarmManager(Context context) {
        return context.getSystemService(AlarmManager.class);
    }

    @Provides
    @Singleton
    static AudioManager provideAudioManager(Context context) {
        return context.getSystemService(AudioManager.class);
    }

    @Provides
    @Singleton
    static ConnectivityManager provideConnectivityManager(Context context) {
        return context.getSystemService(ConnectivityManager.class);
    }

    @Provides
    @Singleton
    static ContentResolver provideContentResolver(Context context) {
        return context.getContentResolver();
    }

    @Provides
    @Singleton
    static DevicePolicyManager provideDevicePolicyManager(Context context) {
        return context.getSystemService(DevicePolicyManager.class);
    }

    @Provides
    @Singleton
    static DisplayManager provideDisplayManager(Context context) {
        return context.getSystemService(DisplayManager.class);
    }

    @Singleton
    @Provides
    static KeyguardManager provideKeyguardManager(Context context) {
        return context.getSystemService(KeyguardManager.class);
    }

    @Singleton
    @Provides
    static LauncherApps provideLauncherApps(Context context) {
        return context.getSystemService(LauncherApps.class);
    }

    @Singleton
    @Provides
    static NotificationManager provideNotificationManager(Context context) {
        return context.getSystemService(NotificationManager.class);
    }

    @Singleton
    @Provides
    static PackageManager providePackageManager(Context context) {
        return context.getPackageManager();
    }

    @Singleton
    @Provides
    static PowerManager providePowerManager(Context context) {
        return context.getSystemService(PowerManager.class);
    }

    @Provides
    @Main
    static Resources provideResources(Context context) {
        return context.getResources();
    }

    @Singleton
    @Provides
    static ShortcutManager provideShortcutManager(Context context) {
        return context.getSystemService(ShortcutManager.class);
    }

    @Provides
    @Singleton
    @Nullable
    static TelecomManager provideTelecomManager(Context context) {
        return context.getSystemService(TelecomManager.class);
    }

    @Provides
    @Singleton
    static TelephonyManager provideTelephonyManager(Context context) {
        return context.getSystemService(TelephonyManager.class);
    }

    @Provides
    @Singleton
    @Nullable
    static Vibrator provideVibrator(Context context) {
        return context.getSystemService(Vibrator.class);
    }

    @Provides
    @Singleton
    static UserManager provideUserManager(Context context) {
        return context.getSystemService(UserManager.class);
    }

    @Provides
    static WallpaperManager provideWallpaperManager(Context context) {
        return (WallpaperManager) context.getSystemService(Context.WALLPAPER_SERVICE);
    }

    @Provides
    @Singleton
    static WifiManager provideWifiManager(Context context) {
        return context.getSystemService(WifiManager.class);
    }

    @Singleton
    @Provides
    static WindowManager provideWindowManager(Context context) {
        return context.getSystemService(WindowManager.class);
    }

    @Provides
    @Singleton
    static RoleManager provideRoleManager(Context context) {
        return context.getSystemService(RoleManager.class);
    }
}
