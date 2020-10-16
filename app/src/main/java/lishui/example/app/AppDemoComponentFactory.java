package lishui.example.app;

import android.app.Application;
import android.content.ContentProvider;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.AppComponentFactory;

import org.jetbrains.annotations.NotNull;

/**
 * Created by lishui.lin on 20-9-29
 */
@RequiresApi(api = Build.VERSION_CODES.P)
public class AppDemoComponentFactory extends AppComponentFactory {

    public AppDemoComponentFactory() {
        super();
    }

    @NonNull
    @Override
    public Application instantiateApplicationCompat(
            @NonNull ClassLoader cl, @NonNull String className)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Application app = super.instantiateApplicationCompat(cl, className);

        if (app instanceof ContextInitializer) {
            ((ContextInitializer) app).setContextAvailableCallback(DependencyImpl::register);
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
            ((ContextInitializer) contentProvider).setContextAvailableCallback(DependencyImpl::register);
        }

        return contentProvider;
    }

    /**
     * A callback that receives a Context when one is ready.
     */
    public interface ContextAvailableCallback {
        void onContextAvailable(final Context applicationContext, final App application);
    }

    /**
     * Implemented in classes that get started by the system before a context is available.
     */
    public interface ContextInitializer {
        void setContextAvailableCallback(@NotNull ContextAvailableCallback callback);
    }
}
