package lishui.example.app;

import android.content.Context;

import lishui.example.common.SubFactoryImpl;
import lishui.example.common.util.LogUtils;
import lishui.example.common.util.PermissionUtils;
import lishui.example.common.util.ProfileProperties;

/**
 * Created by lishui.lin on 20-9-29
 */
class FactoryImpl extends Factory {

    private static final String TAG = "FactoryImpl";

    private App mApplication;
    private Context mApplicationContext;
    private ProfileProperties mProfileProperties;

    public static void register(final Context applicationContext, final App application) {
        if (sRegistered || Factory.get() != null) {
            LogUtils.INSTANCE.i(TAG, "FactoryImpl only call once, stop it.");
            return;
        }

        final FactoryImpl factory = new FactoryImpl();
        Factory.setInstance(factory);
        sRegistered = true;

        // At this point Factory is published. Services can now get initialized and depend on
        // Factory.get().
        factory.mApplication = application;
        factory.mApplicationContext = applicationContext;
        factory.mProfileProperties = new ProfileProperties();

        SubFactoryImpl.init(applicationContext);
        factory.mProfileProperties.init(applicationContext);

        if (PermissionUtils.hasRequiredPermissions()) {
            factory.onRequiredPermissionsAcquired();
        } else {
            LogUtils.INSTANCE.d(TAG, "lost some permissions");
        }
    }

    @Override
    public void onRequiredPermissionsAcquired() {
        if (sInitialized) {
            return;
        }
        sInitialized = true;

//        mApplication.initializeSync(this);
//        final Thread asyncInitialization = new Thread() {
//            @Override
//            public void run() {
//                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
//                mApplication.initializeAsync(FactoryImpl.this);
//            }
//        };
//        asyncInitialization.start();
    }

    @Override
    public Context getAppContext() {
        return mApplicationContext;
    }

    @Override
    public App getApplication() {
        return mApplication;
    }

    @Override
    public ProfileProperties getProfileProperties() {
        return mProfileProperties;
    }
}
