package com.avichai98.preferencenews;

import android.app.Application;
import android.util.Log;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ppp", "app");
    }
}
