package lishui.example.app;

import android.content.Context;
import android.content.SharedPreferences;

import lishui.example.app.messaging.MessagingLoader;
import lishui.example.app.work.WorkScheduler;
import lishui.example.app.wrapper.PackageManagerWrapper;
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
    private static final String SHARED_PREFERENCES_NAME = "app_demo_sp";
    protected static final int PERMISSION_DEFAULT_TYPE = 0;

    private App mApplication;
    private Context mAppContext;
    private ProfileProperties mProfileProperties;
    private AppRepository mAppRepository;
    private WorkScheduler mWorkScheduler;
    private SharedPreferences sharedPreferences;
    private PackageManagerWrapper packageManagerWrapper;
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
        factory.mAppContext = applicationContext;
        factory.mProfileProperties = new ProfileProperties();
        factory.mAppRepository = new AppRepository(applicationContext);
        factory.mWorkScheduler = new WorkScheduler();
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
        return mAppContext;
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
    public WorkScheduler getWorkScheduler() {
        return mWorkScheduler;
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = mAppContext.getSharedPreferences(
                    SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    @Override
    public PackageManagerWrapper getPackageManagerWrapper() {
        if (packageManagerWrapper == null) {
            packageManagerWrapper = new PackageManagerWrapper();
        }
        return packageManagerWrapper;
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
        //LogUtils.d(TAG, "onRequiredPermissionsAcquired type=" + type);
    }
}
