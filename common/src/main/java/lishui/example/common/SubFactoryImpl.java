package lishui.example.common;

import android.content.Context;

import lishui.example.common.util.LogUtils;

/**
 * Created by lishui.lin on 20-9-29
 */
public class SubFactoryImpl extends SubFactory {

    private static final String TAG = "SubFactoryImpl";

    private SubFactoryHost mHost;
    private Context mAppContext;
    private UiIntents mUIIntents;

    public static void init(Context appContext, SubFactoryHost host) {
        if (sInitialized || SubFactory.get() != null) {
            LogUtils.i(TAG, "SubFactoryImpl only call once, stop it.");
            return;
        }

        final SubFactoryImpl factory = new SubFactoryImpl();
        SubFactory.setInstance(factory);
        sInitialized = true;

        factory.mHost = host;
        factory.mAppContext = appContext;
        factory.mUIIntents = new UiIntents();
    }

    @Override
    public SubFactoryHost getSubFactoryHost() {
        return mHost;
    }

    @Override
    public Context getAppContext() {
        return mAppContext;
    }

    @Override
    public UiIntents getUIIntents() {
        return mUIIntents;
    }
}
