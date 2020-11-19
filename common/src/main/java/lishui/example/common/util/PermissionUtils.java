package lishui.example.common.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Hashtable;

import lishui.example.common.SubDependency;
import lishui.example.common.UiIntents;

/**
 * Created by lishui.lin on 20-9-28
 */
public class PermissionUtils {

    private static Hashtable<String, Integer> sPermissions = new Hashtable<>();

    public static boolean hasPermission(final String permission) {
        if (Utilities.isAtLeastM()) {
            if (!sPermissions.containsKey(permission)
                    || sPermissions.get(permission) == PackageManager.PERMISSION_DENIED) {
                final Context context = SubDependency.get().getAppContext();
                final int permissionState = context.checkSelfPermission(permission);
                sPermissions.put(permission, permissionState);
            }
            return sPermissions.get(permission) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    /**
     * Does the app have all the specified permissions
     */
    public static boolean hasPermissions(final String[] permissions) {
        for (final String permission : permissions) {
            if (!hasPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasPhonePermission() {
        return hasPermission(Manifest.permission.READ_PHONE_STATE);
    }

    public static boolean hasSmsPermission() {
        return hasPermission(Manifest.permission.READ_SMS);
    }

    public static boolean hasStoragePermission() {
        // Note that READ_EXTERNAL_STORAGE and WRITE_EXTERNAL_STORAGE are granted or denied together.
        return hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public static String[] getMissingPermissions(final String[] permissions) {
        final ArrayList<String> missingList = new ArrayList<String>();
        for (final String permission : permissions) {
            if (!hasPermission(permission)) {
                missingList.add(permission);
            }
        }

        final String[] missingArray = new String[missingList.size()];
        missingList.toArray(missingArray);
        return missingArray;
    }

    private static String[] sRequiredPermissions = new String[]{
            // Required to read existing SMS threads
            Manifest.permission.READ_SMS,
            // Required for knowing the phone number, number of SIMs, etc.
            Manifest.permission.READ_PHONE_STATE,
            // This is not strictly required
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    /**
     * Does the app have the minimum set of permissions required to operate.
     */
    public static boolean hasRequiredPermissions() {
        return hasPermissions(sRequiredPermissions);
    }

    public static String[] getMissingRequiredPermissions() {
        return getMissingPermissions(sRequiredPermissions);
    }

    public static boolean redirectToPermissionCheckIfNeeded(final Activity activity) {
        if (!hasRequiredPermissions()) {
            UiIntents.get().launchPermissionCheckActivity(activity);
        } else {
            // No redirect performed
            return false;
        }

        //activity.finish();
        return true;
    }

}
