package com.tsystem.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by saransh on 17/04/18.
 */

@Scope
@Retention(RetentionPolicy.CLASS)
public @interface ApplicationScope {
}
