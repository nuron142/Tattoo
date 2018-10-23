package com.sunilk.tattoo.di

import com.sunilk.tattoo.ui.TattooApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Sunil on 20/10/18.
 */

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        ActivityBindingModule::class
    ]
)
interface AppComponent : AndroidInjector<TattooApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TattooApplication): Builder

        fun build(): AppComponent
    }

}