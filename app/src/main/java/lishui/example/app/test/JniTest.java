package lishui.example.app.test;

import lishui.example.common.util.LogUtils;

/**
 * @author lishui.lin
 * Created it on 21-2-23
 */
public class JniTest {

    static {
        // 静态加载native库
        System.loadLibrary("jni-test");
    }

    public static void invokeByJNI(String msgFromJni) {
        LogUtils.d("invoke this method by JNI");
    }

    public native String getNativeStr();

    public native void setNativeStr(String nativeStr);
}
