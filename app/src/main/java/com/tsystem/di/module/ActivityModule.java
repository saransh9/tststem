package com.tsystem.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.tsystem.di.scope.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by saransh on 17/04/18.
 */

@Module
public class ActivityModule {
    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context context() {
        return activity;
    }

    @Provides
    AppCompatActivity activity() {
        return activity;
    }
}
