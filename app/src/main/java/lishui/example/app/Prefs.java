/*
 * Copyright (C) 2015 The Android Open Source Project
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

package lishui.example.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.Set;

/**
 * A helper class to store simple preferences for AppDemo.
 */
public final class Prefs {
    private Prefs() {
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            Key.DEBUG_MODE_ENABLED
    })
    public @interface Key {
        String DEBUG_MODE_ENABLED = "debugModeEnabled";
    }

    public static boolean getBoolean(Context context, @Key String key, boolean defaultValue) {
        return get(context).getBoolean(key, defaultValue);
    }

    public static void putBoolean(Context context, @Key String key, boolean value) {
        get(context).edit().putBoolean(key, value).apply();
    }

    public static int getInt(Context context, @Key String key, int defaultValue) {
        return get(context).getInt(key, defaultValue);
    }

    public static void putInt(Context context, @Key String key, int value) {
        get(context).edit().putInt(key, value).apply();
    }

    public static long getLong(Context context, @Key String key, long defaultValue) {
        return get(context).getLong(key, defaultValue);
    }

    public static void putLong(Context context, @Key String key, long value) {
        get(context).edit().putLong(key, value).apply();
    }

    public static String getString(Context context, @Key String key, String defaultValue) {
        return get(context).getString(key, defaultValue);
    }

    public static void putString(Context context, @Key String key, String value) {
        get(context).edit().putString(key, value).apply();
    }

    public static void putStringSet(Context context, @Key String key, Set<String> value) {
        get(context).edit().putStringSet(key, value).apply();
    }

    public static Set<String> getStringSet(
            Context context, @Key String key, Set<String> defaultValue) {
        return get(context).getStringSet(key, defaultValue);
    }

    public static Map<String, ?> getAll(Context context) {
        return get(context).getAll();
    }

    public static void remove(Context context, @Key String key) {
        get(context).edit().remove(key).apply();
    }

    public static void registerListener(Context context,
                                        OnSharedPreferenceChangeListener listener) {
        get(context).registerOnSharedPreferenceChangeListener(listener);
    }

    public static void unregisterListener(Context context,
                                          OnSharedPreferenceChangeListener listener) {
        get(context).unregisterOnSharedPreferenceChangeListener(listener);
    }

    public static SharedPreferences get(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }
}