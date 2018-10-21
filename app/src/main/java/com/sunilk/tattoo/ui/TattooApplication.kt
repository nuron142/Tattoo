package com.sunilk.tattoo.ui

import android.app.Application
import android.util.Log
import com.sunilk.tattoo.di.AppComponent
import com.sunilk.tattoo.di.DaggerAppComponent
import com.sunilk.tattoo.ui.activity.search.TattooSearchActivityViewModel
import io.reactivex.plugins.RxJavaPlugins


/**
 * Created by Sunil on 20/10/18.
 */
class TattooApplication : Application() {

    val TAG: String = TattooApplication::class.java.simpleName
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        setUpDaggerModule()

        RxJavaPlugins.setErrorHandler { e -> Log.d(TAG, "Error: " + e.message) }
    }

    private fun setUpDaggerModule() {

        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()

        appComponent.inject(this)
    }
}