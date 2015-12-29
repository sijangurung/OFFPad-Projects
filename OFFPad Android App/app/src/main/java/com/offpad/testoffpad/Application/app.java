package com.offpad.testoffpad.Application;

import android.app.Application;
import com.activeandroid.ActiveAndroid;
/**
 * Created by djlophu on 03/03/15.
 */
public class app extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

}
