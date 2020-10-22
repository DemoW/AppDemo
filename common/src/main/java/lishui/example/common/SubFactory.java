package lishui.example.common;

import android.content.Context;

import lishui.example.common.util.LogUtils;

/**
 * Created by lishui.lin on 20-9-29
 */
public abstract class SubFactory {
    private static final String TAG = "SubFactory";

    private static volatile SubFactory sInstance;
    protected static boolean sInitialized;

    public static SubFactory get() {
        return sInstance;
    }

    protected static void setInstance(final SubFactory factory) {
        if (!sInitialized) {
            sInstance = factory;
            LogUtils.d(TAG, "SubFactory setInstance successfully");
        }
    }

    public abstract SubFactoryHost getSubFactoryHost();
    public abstract Context getAppContext();
    public abstract UIIntents getUIIntents();
}
