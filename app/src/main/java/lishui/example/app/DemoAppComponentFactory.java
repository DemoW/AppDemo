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

package lishui.example.app;

import android.app.Application;
import android.content.ContentProvider;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.AppComponentFactory;

/**
 * Implementation of AppComponentFactory that injects into constructors.
 * <p>
 * This class sets up dependency injection when creating our application.
 * <p>
 * Services support dependency injection into their constructors.
 * <p>
 * ContentProviders support injection into member variables - _not_ constructors.
 */
public class DemoAppComponentFactory extends AppComponentFactory {

    private static final String TAG = "DemoAppComponentFactory";

    public DemoAppComponentFactory() {
        super();
    }

    @NonNull
    @Override
    public Application instantiateApplicationCompat(
            @NonNull ClassLoader cl, @NonNull String className)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Application app = super.instantiateApplicationCompat(cl, className);
        if (app instanceof ContextInitializer) {
            /*DemoDaggerFactory.getInstance().getRootComponent().inject(
                                DemoAppComponentFactory.this);*/
            ((ContextInitializer) app).setContextAvailableCallback(
                    DemoDaggerFactory::createFromConfig
            );
        }

        return app;
    }

    @NonNull
    @Override
    public ContentProvider instantiateProviderCompat(
            @NonNull ClassLoader cl, @NonNull String className)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        ContentProvider contentProvider = super.instantiateProviderCompat(cl, className);
        if (contentProvider instanceof ContextInitializer) {
            //DemoDaggerFactory.getInstance().getRootComponent().inject(contentProvider);
            ((ContextInitializer) contentProvider).setContextAvailableCallback(
                    DemoDaggerFactory::createFromConfig
            );
        }

        return contentProvider;
    }

    /**
     * A callback that receives a Context when one is ready.
     */
    public interface ContextAvailableCallback {
        void onContextAvailable(Context context);
    }

    /**
     * Implemented in classes that get started by the system before a context is available.
     */
    public interface ContextInitializer {
        void setContextAvailableCallback(ContextAvailableCallback callback);
    }
}
