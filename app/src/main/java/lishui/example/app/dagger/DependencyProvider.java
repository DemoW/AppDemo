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

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Choreographer;
import android.view.LayoutInflater;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lishui.example.app.Prefs;
import lishui.example.app.dagger.qualifiers.Main;

/**
 * Provides dependencies for the root component of app injection.
 * <p>
 * Only App owned classes and instances should go in here. Other, framework-owned classes
 * should go in {@link SystemServicesModule}.
 */
@Module
public class DependencyProvider {

    @Provides
    @Main
    public SharedPreferences provideSharePreferences(Context context) {
        return Prefs.get(context);
    }

    @Singleton
    @Provides
    public LayoutInflater providerLayoutInflater(Context context) {
        return LayoutInflater.from(context);
    }

    @Singleton
    @Provides
    public Choreographer providesChoreographer() {
        return Choreographer.getInstance();
    }
}
