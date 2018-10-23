package com.sunilk.tattoo.di

import android.content.Context
import com.sunilk.tattoo.ui.TattooApplication
import dagger.Module
import dagger.Provides

/**
 * Created by Sunil on 20/10/18.
 */

@Module
class AppModule {

    @Provides
    fun provideContext(tattooApplication: TattooApplication): Context {
        return tattooApplication.applicationContext
    }
}