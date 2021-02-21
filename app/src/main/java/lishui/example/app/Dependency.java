/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package lishui.example.app;

import android.os.Handler;
import android.os.Looper;
import android.util.ArrayMap;

import androidx.annotation.VisibleForTesting;

import java.util.function.Consumer;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.Subcomponent;
import lishui.example.app.dagger.qualifiers.Background;
import lishui.example.app.dagger.qualifiers.Main;

/**
 * Class to handle ugly dependencies throughout app until we determine the
 * long-term dependency injection solution.
 * <p>
 * All classes used here are expected to manage their own lifecycle, meaning if
 * they have no clients they should not have any registered resources like bound
 * services, registered receivers, etc.
 */
public class Dependency {
    /**
     * Key for getting a the main looper.
     */
    private static final String MAIN_LOOPER_NAME = "main_looper";
    /**
     * Key for getting a background Looper for background work.
     */
    private static final String BG_LOOPER_NAME = "background_looper";
    /**
     * Generic handler on the main thread.
     */
    private static final String MAIN_HANDLER_NAME = "main_handler";

    /**
     * Key for getting a background Looper for background work.
     */
    private static final DependencyKey<Looper> BG_LOOPER = new DependencyKey<>(BG_LOOPER_NAME);
    /**
     * Key for getting a mainer Looper.
     */
    private static final DependencyKey<Looper> MAIN_LOOPER = new DependencyKey<>(MAIN_LOOPER_NAME);
    /**
     * Generic handler on the main thread.
     */
    private static final DependencyKey<Handler> MAIN_HANDLER =
            new DependencyKey<>(MAIN_HANDLER_NAME);

    private final ArrayMap<Object, Object> mDependencies = new ArrayMap<>();
    private final ArrayMap<Object, LazyDependencyCreator> mProviders = new ArrayMap<>();

    @Inject
    @Background
    Lazy<Looper> mBgLooper;
    @Inject
    @Background
    Lazy<Handler> mBgHandler;
    @Inject
    @Main
    Lazy<Looper> mMainLooper;
    @Inject
    @Main
    Lazy<Handler> mMainHandler;

    @Inject
    Lazy<AppRepository> mAppRepository;

    @Inject
    public Dependency() {
    }

    /**
     * Initialize Dependency.
     */
    protected void start() {
        // TODO: Think about ways to push these creation rules out of Dependency to cut down
        // on imports.
        mProviders.put(BG_LOOPER, mBgLooper::get);
        mProviders.put(MAIN_LOOPER, mMainLooper::get);
        mProviders.put(MAIN_HANDLER, mMainHandler::get);

        mProviders.put(AppRepository.class, mAppRepository::get);

        sDependency = this;
    }

    protected final <T> T getDependency(Class<T> cls) {
        return getDependencyInner(cls);
    }

    protected final <T> T getDependency(DependencyKey<T> key) {
        return getDependencyInner(key);
    }

    private synchronized <T> T getDependencyInner(Object key) {
        @SuppressWarnings("unchecked")
        T obj = (T) mDependencies.get(key);
        if (obj == null) {
            obj = createDependency(key);
            mDependencies.put(key, obj);
        }
        return obj;
    }

    @VisibleForTesting
    protected <T> T createDependency(Object cls) {
        final boolean expression = cls instanceof DependencyKey<?> || cls instanceof Class<?>;
        if (!expression) {
            throw new IllegalArgumentException();
        }

        @SuppressWarnings("unchecked")
        LazyDependencyCreator<T> provider = mProviders.get(cls);
        if (provider == null) {
            throw new IllegalArgumentException("Unsupported dependency " + cls
                    + ". " + mProviders.size() + " providers known.");
        }
        return provider.createDependency();
    }

    private static Dependency sDependency;

    /**
     * Interface for a class that can create a dependency. Used to implement laziness
     *
     * @param <T> The type of the dependency being created
     */
    private interface LazyDependencyCreator<T> {
        T createDependency();
    }

    private <T> void destroyDependency(Class<T> cls, Consumer<T> destroy) {
        T dep = (T) mDependencies.remove(cls);
        if (dep != null && destroy != null) {
            destroy.accept(dep);
        }
    }

    /**
     * Used in separate process teardown to ensure the context isn't leaked.
     * <p>
     * TODO: Remove once PreferenceFragment doesn't reference getActivity()
     * anymore and these context hacks are no longer needed.
     */
    public static void clearDependencies() {
        sDependency = null;
    }

    /**
     * Checks to see if a dependency is instantiated, if it is it removes it from
     * the cache and calls the destroy callback.
     */
    public static <T> void destroy(Class<T> cls, Consumer<T> destroy) {
        sDependency.destroyDependency(cls, destroy);
    }

    /**
     * @deprecated see docs/dagger.md
     */
    @Deprecated
    public static <T> T get(Class<T> cls) {
        return sDependency.getDependency(cls);
    }

    /**
     * @deprecated see docs/dagger.md
     */
    @Deprecated
    public static <T> T get(DependencyKey<T> cls) {
        return sDependency.getDependency(cls);
    }

    public static final class DependencyKey<V> {
        private final String mDisplayName;

        public DependencyKey(String displayName) {
            mDisplayName = displayName;
        }

        @Override
        public String toString() {
            return mDisplayName;
        }
    }

    @Subcomponent
    public interface DependencyInjector {
        void injectDependency(Dependency dependency);
    }
}
