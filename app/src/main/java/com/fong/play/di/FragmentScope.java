package com.fong.play.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by FANGDINGJIE
 * 2018/3/20.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface FragmentScope {
}
