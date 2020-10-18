package lishui.example.app;

import android.content.Context;
import android.content.SharedPreferences;

import lishui.example.app.messaging.MessagingLoader;
import lishui.example.app.net.NetworkManager;
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
class DependencyImpl extends Dependency implements SubFactoryHost {

    private static final String TAG = "DependencyImpl";
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
    private NetworkManager mNetworkManager;

    public static void register(final Context appContext, final App application) {
        if (sRegistered || Dependency.get() != null) {
            LogUtils.i(TAG, "DependencyImpl only call once, stop it.");
            return;
        }

        final DependencyImpl dependency = new DependencyImpl();
        Dependency.setInstance(dependency);
        sRegistered = true;

        // At this point Factory is published. Services can now get initialized and depend on
        // Dependency.get().
        dependency.mApplication = application;
        dependency.mAppContext = appContext;
        dependency.mProfileProperties = new ProfileProperties();
        dependency.mAppRepository = new AppRepository(appContext);
        dependency.mWorkScheduler = new WorkScheduler();
        dependency.mMessagingLoader = new MessagingLoader();

        SubFactoryImpl.init(appContext, dependency);
        //dependency.mProfileProperties.init(appContext);

        if (PermissionUtils.hasRequiredPermissions()) {
            dependency.onRequiredPermissionsAcquired(PERMISSION_DEFAULT_TYPE);
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
    public NetworkManager getNetworkManager() {
        if (mNetworkManager == null) {
            mNetworkManager = new NetworkManager();
        }
        return mNetworkManager;
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
