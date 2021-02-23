package lishui.example.app.test;

/**
 * @author lishui.lin
 * Created it on 21-2-23
 */
public class JniTest {

    static {
        // 静态加载native库
        System.loadLibrary("jni-test");
    }
    public native String getNativeStr();

    public native void setNativeStr(String nativeStr);
}
