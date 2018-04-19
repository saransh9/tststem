package com.tsystem.di.component;


import com.tsystem.ApplicationClass;
import com.tsystem.data.ApiCalls;
import com.tsystem.di.module.ApiModule;
import com.tsystem.di.scope.ApplicationScope;

import dagger.Component;

/**
 * Created by saransh on 17/04/18.
 */

@ApplicationScope
@Component(modules = {ApiModule.class})
public interface ApplicationComponent {

    void inject(ApplicationClass applicationClass);

    ApiCalls getapiCalls();
}
