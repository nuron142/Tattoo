package com.sunilk.tattoo.di

import com.sunilk.tattoo.ui.SpectreApplication
import com.sunilk.tattoo.ui.activity.artistdetail.TattooDetailActivity
import com.sunilk.tattoo.ui.activity.artistdetail.TattooDetailActivityViewModel
import com.sunilk.tattoo.ui.activity.search.TattooSearchActivity
import com.sunilk.tattoo.ui.activity.search.TattooSearchActivityViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Sunil on 10/1/18.
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(spectreApplication: SpectreApplication)

    fun inject(tattooSearchActivity: TattooSearchActivity)

    fun inject(tattooDetailActivity: TattooDetailActivity)

    fun inject(tattooSearchActivityViewModel: TattooSearchActivityViewModel)

    fun inject(tattooDetailActivityViewModel: TattooDetailActivityViewModel)

}