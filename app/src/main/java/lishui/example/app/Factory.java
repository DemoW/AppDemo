package lishui.example.app;

import android.content.Context;

import lishui.example.common.UIIntents;
import lishui.example.common.util.LogUtils;
import lishui.example.common.util.ProfileProperties;

/**
 * Created by lishui.lin on 20-9-29
 */
public abstract class Factory {

    private static final String TAG = "Factory";

    // Making this volatile because on the unit tests, setInstance is called from a unit test
    // thread, and then it's read on the UI thread.
    private static volatile Factory sInstance;
    protected static boolean sRegistered;
    protected static boolean sInitialized;

    public static Factory get() {
        return sInstance;
    }

    protected static void setInstance(final Factory factory) {
        if (!sRegistered) {
            sInstance = factory;
            LogUtils.INSTANCE.d(TAG, "Factory setInstance successfully.");
        }
    }

    public abstract Context getAppContext();
    public abstract App getApplication();
    public abstract ProfileProperties getProfileProperties();
    public abstract void onRequiredPermissionsAcquired();
}
