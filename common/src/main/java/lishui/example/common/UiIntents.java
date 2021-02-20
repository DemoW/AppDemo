package lishui.example.common;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;


public class UiIntents {

    public static UiIntents get() {
        return SubDependency.get().getUIIntents();
    }

    private static final String TAG = "UIIntents";

    private static final String MAIN_CLASS_NAME = "lishui.example.app.ui.MainActivity";

    private static final String ACTION_PERMISSION_CHECKER = "lishui.example.app.action.PERMISSION";
    private static final String ACTION_SEARCH = "lishui.example.app.action.SEARCH";

    public void launchPermissionCheckActivity(final Context context) {
        final Intent intent = new Intent(ACTION_PERMISSION_CHECKER);

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (isValidIntent(intent)) {
            context.startActivity(intent);
        }
    }

    public void launchMainUiActivity(final Context context) {
        final Intent intent = new Intent();
        intent.setComponent(ComponentName.createRelative(
                context.getPackageName(), MAIN_CLASS_NAME));

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (isValidIntent(intent)) {
            context.startActivity(intent);
        }
    }

    public void launchSearch(Context context) {

        final Intent intent = new Intent(ACTION_SEARCH);

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (isValidIntent(intent)) {
            context.startActivity(intent);
        }
    }

    public void launchBrowser(Context context, String webLink) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(webLink);
        intent.setData(uri);

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (isValidIntent(intent)) {
            context.startActivity(intent);
        }
    }

    private boolean isValidIntent(Intent intent) {
        return intent != null && intent.resolveActivity(
                SubDependency.get().getAppContext().getPackageManager()) != null;
    }
}