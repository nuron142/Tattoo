package com.sunilk.tattoo.di

import com.sunilk.tattoo.ui.activity.search.TattooSearchActivity
import com.sunilk.tattoo.ui.activity.tattoodetail.TattooDetailActivity
import com.sunilk.tattoo.ui.activity.tattoodetail.TattooDetailActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Sunil on 20/10/18.
 */

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun tattooSearchActivity(): TattooSearchActivity


    @ActivityScope
    @ContributesAndroidInjector(modules = [TattooDetailActivityModule::class])
    abstract fun tattooDetailActivity(): TattooDetailActivity
}
