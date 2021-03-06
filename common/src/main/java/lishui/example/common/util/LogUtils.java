package lishui.example.common.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by lishui.lin on 20-9-30
 */
public class LogUtils {

    private static final String DEFAULT_TAG = "AppDemo";

    public static void d(final String msg) {
        println(android.util.Log.DEBUG, "", msg);
    }

    public static void d(final String tag, final String msg) {
        println(android.util.Log.DEBUG, tag, msg);
    }

    public static void d(final String tag, final String msg, boolean isDebug) {
        if (isDebug) println(android.util.Log.DEBUG, tag, msg);
    }

    public static void i(final String tag, final String msg) {
        println(android.util.Log.INFO, tag, msg);
    }

    public static void w(final String tag, final String msg) {
        println(android.util.Log.WARN, tag, msg);
    }

    public static void w(final String tag, final String msg, final Throwable tr) {
        println(android.util.Log.WARN, tag, msg);
        println(android.util.Log.WARN, tag, android.util.Log.getStackTraceString(tr));
    }

    public static void e(final String tag, final String msg) {
        println(android.util.Log.ERROR, tag, msg);
    }

    public static void e(final String tag, final String msg, final Throwable tr) {
        println(android.util.Log.ERROR, tag, msg);
        println(android.util.Log.ERROR, tag, android.util.Log.getStackTraceString(tr));
    }

    public static void logThreadName(String tag) {
        println(android.util.Log.DEBUG, tag, "Current Thread Name=" + Thread.currentThread().getName());
    }

    public static void showStackTrace() {
        d(Log.getStackTraceString(new Throwable()));
    }

    /**
     * Low-level logging call.
     *
     * @param level The priority/type of this log message
     * @param tag   Used to identify the source of a log message.  It usually identifies
     *              the class or activity where the log call occurs.
     * @param msg   The message you would like logged.
     */
    private static void println(final int level, final String tag, final String msg) {
        if (TextUtils.isEmpty(tag)) {
            android.util.Log.println(level, DEFAULT_TAG, msg);
        } else {
            android.util.Log.println(level, DEFAULT_TAG, tag + " # " + msg);
        }

        /*if (level >= android.util.Log.DEBUG) {
            // to do something
        }*/
    }

    /**
     * Checks to see whether or not a log for the specified tag is loggable at the specified level.
     * See {@link android.util.Log#isLoggable(String, int)} for more discussion.
     */
    public static boolean isLoggable(final String tag, final int level) {
        return android.util.Log.isLoggable(tag, level);
    }
}
