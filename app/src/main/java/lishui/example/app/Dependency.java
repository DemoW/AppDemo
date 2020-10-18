package lishui.example.app;

import android.content.Context;
import android.content.SharedPreferences;

import lishui.example.app.messaging.MessagingLoader;
import lishui.example.app.net.NetworkManager;
import lishui.example.app.work.WorkScheduler;
import lishui.example.app.wrapper.PackageManagerWrapper;
import lishui.example.common.util.LogUtils;
import lishui.example.common.util.ProfileProperties;

/**
 * Created by lishui.lin on 20-9-29
 */
public abstract class Dependency {

    private static final String TAG = "Dependency";

    private static volatile Dependency sInstance;
    protected static boolean sRegistered;
    protected static boolean sInitialized;

    public static Dependency get() {
        return sInstance;
    }

    protected static void setInstance(final Dependency dependency) {
        if (!sRegistered) {
            sInstance = dependency;
            LogUtils.d(TAG, "Dependency setInstance successfully.");
        }
    }

    public abstract Context getAppContext();
    public abstract App getApplication();
    public abstract ProfileProperties getProfileProperties();
    public abstract AppRepository getAppRepository();
    public abstract WorkScheduler getWorkScheduler();
    public abstract SharedPreferences getSharedPreferences();
    public abstract PackageManagerWrapper getPackageManagerWrapper();
    public abstract MessagingLoader getMessagingLoader();
    public abstract NetworkManager getNetworkManager();
}
