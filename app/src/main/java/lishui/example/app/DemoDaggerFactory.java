/*
 * Copyright (C) 2016 The Android Open Source Project
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
 * limitations under the License
 */

package lishui.example.app;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import dagger.Module;
import dagger.Provides;
import lishui.example.app.dagger.AppRootComponent;
import lishui.example.app.dagger.DaggerAppRootComponent;
import lishui.example.app.dagger.DependencyProvider;

/**
 * Class factory to provide customizable components.
 */
public class DemoDaggerFactory {
    private static final String TAG = "DemoDaggerFactory";

    static DemoDaggerFactory mFactory;
    private AppRootComponent mRootComponent;

    public static <T extends DemoDaggerFactory> T getInstance() {
        return (T) mFactory;
    }

    public static void createFromConfig(Context context) {
        if (mFactory != null) {
            return;
        }

        final String clsName = context.getString(R.string.config_appFactoryComponent);
        if (clsName == null || clsName.length() == 0) {
            throw new RuntimeException("No factory component configured");
        }

        try {
            Class<?> cls = null;
            cls = context.getClassLoader().loadClass(clsName);
            mFactory = (DemoDaggerFactory) cls.newInstance();
            mFactory.init(context);
        } catch (Throwable t) {
            Log.w(TAG, "Error creating SystemUIFactory component: " + clsName, t);
            throw new RuntimeException(t);
        }
    }

    @VisibleForTesting
    static void cleanup() {
        mFactory = null;
    }

    public DemoDaggerFactory() {
    }

    private void init(Context context) {
        mRootComponent = buildSystemUIRootComponent(context);

        // Every other part of our codebase currently relies on Dependency, so we
        // really need to ensure the Dependency gets initialized early on.

        Dependency dependency = new Dependency();
        mRootComponent.createDependency().injectDependency(dependency);
        dependency.start();
    }

    protected void initWithRootComponent(@NonNull AppRootComponent rootComponent) {
        if (mRootComponent != null) {
            throw new RuntimeException("Root component can be set only once.");
        }

        mRootComponent = rootComponent;
    }

    protected AppRootComponent buildSystemUIRootComponent(Context context) {
        return DaggerAppRootComponent.builder()
                .dependencyProvider(new DependencyProvider())
                .contextHolder(new ContextHolder(context))
                .build();
    }

    public AppRootComponent getRootComponent() {
        return mRootComponent;
    }

    @Module
    public static class ContextHolder {
        private Context mContext;

        public ContextHolder(Context context) {
            mContext = context;
        }

        @Provides
        public Context provideContext() {
            return mContext;
        }
    }
}
