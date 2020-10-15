package lishui.example.app;

import android.content.Context;
import android.content.SharedPreferences;

import kotlin.Lazy;
import lishui.example.app.messaging.MessagingLoader;
import lishui.example.app.work.WorkScheduler;
import lishui.example.app.wrapper.PackageManagerWrapper;
import lishui.example.common.util.LogUtils;
import lishui.example.common.util.ProfileProperties;

/**
 * Created by lishui.lin on 20-9-29
 */
public abstract class Factory {

    private static final String TAG = "Factory";

    private static volatile Factory sInstance;
    protected static boolean sRegistered;
    protected static boolean sInitialized;

    public static Factory get() {
        return sInstance;
    }

    protected static void setInstance(final Factory factory) {
        if (!sRegistered) {
            sInstance = factory;
            LogUtils.d(TAG, "Factory setInstance successfully.");
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
