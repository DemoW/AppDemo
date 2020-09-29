package lishui.example.common;

import android.content.Context;


public abstract class UIIntents {

    public static UIIntents get() {
        return SubFactory.get().getUIIntents();
    }

    protected static final String TAG = "UIIntents";
    protected static final String MAIN_CLASS_NAME = "lishui.example.app.ui.MainActivity";

    public abstract void launchPermissionCheckActivity(final Context context);
    public abstract void launchMainUiActivity(final Context context);
}