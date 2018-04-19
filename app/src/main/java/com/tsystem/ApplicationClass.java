package com.tsystem;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.tsystem.di.component.ApplicationComponent;
import com.tsystem.di.component.DaggerApplicationComponent;
import com.tsystem.di.module.ApiModule;

/**
 * Created by saransh on 17/04/18.
 */

public class ApplicationClass extends Application {
    ApplicationComponent applicationComponent;
    public static final String API_URL = "https://api.unsplash.com/";

    public static ApplicationClass get(AppCompatActivity activity) {
        return (ApplicationClass) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .apiModule(new ApiModule(API_URL))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
