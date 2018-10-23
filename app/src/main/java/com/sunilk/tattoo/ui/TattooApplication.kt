package com.sunilk.tattoo.ui

import android.app.Activity
import android.app.Application
import android.util.Log
import com.sunilk.tattoo.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject


/**
 * Created by Sunil on 20/10/18.
 */
class TattooApplication : Application(), HasActivityInjector {

    val TAG: String = TattooApplication::class.java.simpleName

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = androidInjector

    override fun onCreate() {
        super.onCreate()
        setUpDaggerModule()

        RxJavaPlugins.setErrorHandler { e -> Log.d(TAG, "Error: " + e.message) }
    }

    private fun setUpDaggerModule() {

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }
}