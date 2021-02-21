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

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lishui.example.app.AppRepository;

/**
 * A dagger module for injecting components of AppDemo that are not overridden by the AppDemo
 * implementation.
 */
@Module(includes = {
        ConcurrencyModule.class,
})
public abstract class AppDemoModule {

    @Provides
    @Singleton
    public static AppRepository provideAppRepository(Context context) {
        return new AppRepository(context);
    }
}
