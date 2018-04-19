package com.tsystem.di.component;

import android.content.Context;

import com.tsystem.ui.activity.MainActivity;
import com.tsystem.di.module.ActivityModule;
import com.tsystem.di.scope.ActivityContext;
import com.tsystem.di.scope.PerActivity;

import dagger.Component;

/**
 * Created by saransh on 17/04/18.
 */

@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {

    @ActivityContext
    Context getContext();

    void inject(MainActivity activity);
}
