package com.tsystem.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by saransh on 17/04/18.
 */

@Qualifier
@Retention(RetentionPolicy.CLASS)
public @interface ActivityContext {
}
