package lishui.example.app;

import android.content.Context;

import lishui.example.app.messaging.MessagingLoader;
import lishui.example.common.SubFactoryHost;
import lishui.example.common.SubFactoryImpl;
import lishui.example.common.util.LogUtils;
import lishui.example.common.util.PermissionUtils;
import lishui.example.common.util.ProfileProperties;

/**
 * Created by lishui.lin on 20-9-29
 */
class FactoryImpl extends Factory implements SubFactoryHost {

    private static final String TAG = "FactoryImpl";
    protected static final int PERMISSION_DEFAULT_TYPE = 0;

    private App mApplication;
    private Context mApplicationContext;
    private ProfileProperties mProfileProperties;
    private AppRepository mAppRepository;
    private MessagingLoader mMessagingLoader;

    public static void register(final Context applicationContext, final App application) {
        if (sRegistered || Factory.get() != null) {
            LogUtils.i(TAG, "FactoryImpl only call once, stop it.");
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
        factory.mAppRepository = new AppRepository(applicationContext);
        factory.mMessagingLoader = new MessagingLoader();

        SubFactoryImpl.init(applicationContext, factory);
        factory.mProfileProperties.init(applicationContext);

        if (PermissionUtils.hasRequiredPermissions()) {
            factory.onRequiredPermissionsAcquired(PERMISSION_DEFAULT_TYPE);
        } else {
            LogUtils.d(TAG, "lost some permissions");
        }
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

    @Override
    public AppRepository getAppRepository() {
        return mAppRepository;
    }

    @Override
    public MessagingLoader getMessagingLoader() {
        return mMessagingLoader;
    }

    @Override
    public void onRequiredPermissionsAcquired(int type) {
        if (sInitialized) {
            return;
        }
        sInitialized = true;

        LogUtils.d(TAG, "onRequiredPermissionsAcquired type=" + type);

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
}
