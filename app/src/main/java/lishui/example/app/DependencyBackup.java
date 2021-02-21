package lishui.example.app;

import android.content.Context;
import android.content.SharedPreferences;

import lishui.example.app.net.NetworkManager;
import lishui.example.common.util.LogUtils;

/**
 * Created by lishui.lin on 20-9-29
 */
public abstract class DependencyBackup {

    private static final String TAG = "Dependency";

    private static volatile DependencyBackup sInstance;
    protected static boolean sRegistered;
    protected static boolean sInitialized;

    public static DependencyBackup get() {
        return sInstance;
    }

    protected static void setInstance(final DependencyBackup dependency) {
        if (!sRegistered) {
            sInstance = dependency;
            LogUtils.d(TAG, "Dependency setInstance successfully");
        }
    }

    public abstract Context getAppContext();

    public abstract SharedPreferences getSharedPreferences();

    public abstract NetworkManager getNetworkManager();
}
