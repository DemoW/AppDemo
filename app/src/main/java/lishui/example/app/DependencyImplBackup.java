package lishui.example.app;

import android.content.Context;
import android.content.SharedPreferences;

import lishui.example.app.net.NetworkManager;
import lishui.example.common.SubDependencyImpl;
import lishui.example.common.SubFactoryHost;
import lishui.example.common.util.LogUtils;
import lishui.example.common.util.PermissionUtils;

/**
 * Created by lishui.lin on 20-9-29
 */
class DependencyImplBackup extends DependencyBackup implements SubFactoryHost {

    private static final String TAG = "DependencyImpl";
    private static final String SHARED_PREFERENCES_NAME = "app_demo_sp";
    protected static final int PERMISSION_DEFAULT_TYPE = 0;

    private Context mAppContext;
    private SharedPreferences sharedPreferences;
    private NetworkManager mNetworkManager;

    public static void register(final Context appContext) {
        if (sRegistered || DependencyBackup.get() != null) {
            LogUtils.i(TAG, "DependencyImpl only call once, stop it");
            return;
        }

        final DependencyImplBackup dependency = new DependencyImplBackup();
        DependencyBackup.setInstance(dependency);
        sRegistered = true;

        // At this point Factory is published. Services can now get initialized and depend on
        // Dependency.get().
        dependency.mAppContext = appContext;

        SubDependencyImpl.init(appContext, dependency);

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
    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = mAppContext.getSharedPreferences(
                    SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
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
