package com.example.android.questiontime;

import android.app.Application;
import android.content.Context;

/**
 * A subclass of Application for allowing reference to context in my Topic Enum.
 */

public class App extends Application{

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
