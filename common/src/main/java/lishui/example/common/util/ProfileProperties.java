package lishui.example.common.util;

import android.content.Context;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * Created by lishui.lin on 20-9-29
 */
public class ProfileProperties {

    private static final String TAG = "ProfileProperties";
    private static final String PROFILE_NAME = "profile.properties";

    private Properties mProperties = new Properties();

    public void init(Context context) {
        try {
            mProperties.load(context.getResources().getAssets().open(PROFILE_NAME));
            Set<String> propertyNames = mProperties.stringPropertyNames();
            propertyNames.forEach(name -> LogUtils.INSTANCE.d(TAG, "property name=" + name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return mProperties;
    }
}
