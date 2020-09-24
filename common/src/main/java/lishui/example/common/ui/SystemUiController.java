package lishui.example.common.ui;

import android.view.View;
import android.view.Window;

import java.util.Arrays;

import lishui.example.common.util.Utilities;

/**
 * Utility class to manage various window flags to control system UI.
 */
public class SystemUiController {

    // Various UI states in increasing order of priority
    public static final int UI_STATE_BASE_WINDOW = 0;
    public static final int UI_STATE_ALL_APPS = 1;
    public static final int UI_STATE_WIDGET_BOTTOM_SHEET = 2;
    public static final int UI_STATE_ROOT_VIEW = 3;
    public static final int UI_STATE_OVERVIEW = 4;

    public static final int FLAG_LIGHT_NAV = 1 << 0;
    public static final int FLAG_DARK_NAV = 1 << 1;
    public static final int FLAG_LIGHT_STATUS = 1 << 2;
    public static final int FLAG_DARK_STATUS = 1 << 3;

    private final Window mWindow;
    private final int[] mStates = new int[5];

    public SystemUiController(Window window) {
        mWindow = window;
    }

    // isLight：状态栏或导航栏是否是亮色背景，当为亮色背景时，会将系统栏调暗，反之亦然
    public void updateUiState(int uiState, boolean isLight) {
        updateUiState(uiState, isLight
                ? (FLAG_LIGHT_NAV | FLAG_LIGHT_STATUS) : (FLAG_DARK_NAV | FLAG_DARK_STATUS));
    }

    public void updateUiState(int uiState, int flags) {
        if (mStates[uiState] == flags) {
            return;
        }
        mStates[uiState] = flags;

        int oldFlags = mWindow.getDecorView().getSystemUiVisibility();
        // Apply the state flags in priority order
        int newFlags = oldFlags;
        for (int stateFlag : mStates) {
            if (Utilities.ATLEAST_OREO) {
                if ((stateFlag & FLAG_LIGHT_NAV) != 0) {
                    newFlags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                } else if ((stateFlag & FLAG_DARK_NAV) != 0) {
                    newFlags &= ~(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
                }
            }

            if ((stateFlag & FLAG_LIGHT_STATUS) != 0) {
                newFlags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else if ((stateFlag & FLAG_DARK_STATUS) != 0) {
                newFlags &= ~(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        if (newFlags != oldFlags) {
            mWindow.getDecorView().setSystemUiVisibility(newFlags);
        }
    }

    @Override
    public String toString() {
        return "mStates=" + Arrays.toString(mStates);
    }
}
