package lishui.example.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import lishui.example.common.ui.PermissionCheckActivity;

/**
 * Created by lishui.lin on 20-9-29
 */
public class UIIntentsImpl extends UIIntents {

    @Override
    public void launchPermissionCheckActivity(final Context context) {
        final Intent intent = new Intent(context, PermissionCheckActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void launchMainUiActivity(Context context) {
        final Intent intent = new Intent();
        intent.setComponent(ComponentName.createRelative(
                context.getPackageName(), MAIN_CLASS_NAME));
        context.startActivity(intent);
    }
}
