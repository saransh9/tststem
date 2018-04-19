package com.tsystem.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tsystem.ApplicationClass;
import com.tsystem.di.component.ActivityComponent;
import com.tsystem.di.component.DaggerActivityComponent;
import com.tsystem.di.module.ActivityModule;


/**
 * Created by saransh on 17/04/18.
 */

public abstract class BaseActivity extends AppCompatActivity {

    ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(ApplicationClass.get(this).getApplicationComponent())
                .build();
    }

    public ActivityComponent getActivityComponent()
    {
        return activityComponent;
    }
}
