package lishui.example.common;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;


public class UiIntents {

    public static UiIntents get() {
        return SubFactory.get().getUIIntents();
    }

    private static final String TAG = "UIIntents";

    private static final String MAIN_CLASS_NAME = "lishui.example.app.ui.MainActivity";

    private static final String ACTION_EXO_PLAYER = "lishui.example.player.action.EXO_PLAYER";
    private static final String ACTION_PERMISSION_CHECKER = "lishui.example.app.action.PERMISSION";

    public void launchPermissionCheckActivity(final Context context) {
        final Intent intent = new Intent(ACTION_PERMISSION_CHECKER);

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public void launchMainUiActivity(final Context context) {
        final Intent intent = new Intent();
        intent.setComponent(ComponentName.createRelative(
                context.getPackageName(), MAIN_CLASS_NAME));

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    public void launchExoVideo(Context context) {

        final Intent intent = new Intent(ACTION_EXO_PLAYER);

        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}