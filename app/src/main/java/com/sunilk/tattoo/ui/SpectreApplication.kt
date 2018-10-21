package com.sunilk.tattoo.ui

import android.app.Application
import com.sunilk.tattoo.di.AppComponent
import com.sunilk.tattoo.di.AppModule
import com.sunilk.tattoo.di.DaggerAppComponent
import io.reactivex.plugins.RxJavaPlugins

/**
 * Created by Sunil on 10/1/18.
 */
class SpectreApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        setUpDaggerModule()

        RxJavaPlugins.setErrorHandler { e -> }
    }

    private fun setUpDaggerModule() {

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}