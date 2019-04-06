package com.beeete2.android.androidmrvxexample

import com.beeete2.android.androidmrvxexample.di.DaggerAppComponent
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector() = DaggerAppComponent.builder()
        .application(this)
        .build()

}
