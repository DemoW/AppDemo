package lishui.example.common;

import android.content.Context;

import lishui.example.common.util.LogUtils;

/**
 * Created by lishui.lin on 20-9-29
 */
public abstract class SubDependency {
    private static final String TAG = "SubDependency";

    private static volatile SubDependency sInstance;
    protected static boolean sInitialized;

    public static SubDependency get() {
        return sInstance;
    }

    protected static void setInstance(final SubDependency factory) {
        if (!sInitialized) {
            sInstance = factory;
            LogUtils.d(TAG, "SubDependency setInstance successfully");
        }
    }

    public abstract SubFactoryHost getSubFactoryHost();
    public abstract Context getAppContext();
    public abstract UiIntents getUIIntents();
}
