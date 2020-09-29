package lishui.example.common;

import android.content.Context;

import lishui.example.common.util.LogUtils;

/**
 * Created by lishui.lin on 20-9-29
 */
public class SubFactoryImpl extends SubFactory {

    private static final String TAG = "SubFactoryImpl";

    private Context mApplicationContext;
    private UIIntents mUIIntents;

    public static void init(Context appContext) {
        if (sInitialized || SubFactory.get() != null) {
            LogUtils.INSTANCE.i(TAG, "SubFactoryImpl only call once, stop it.");
            return;
        }

        final SubFactoryImpl factory = new SubFactoryImpl();
        SubFactory.setInstance(factory);
        sInitialized = true;

        factory.mApplicationContext = appContext;
        factory.mUIIntents = new UIIntentsImpl();
    }

    @Override
    public Context getAppContext() {
        return mApplicationContext;
    }

    @Override
    public UIIntents getUIIntents() {
        return mUIIntents;
    }
}
