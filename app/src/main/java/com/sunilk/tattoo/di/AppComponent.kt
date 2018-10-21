package com.sunilk.tattoo.di

import com.sunilk.tattoo.ui.SpectreApplication
import com.sunilk.tattoo.ui.activity.artistdetail.ArtistDetailActivity
import com.sunilk.tattoo.ui.activity.artistdetail.ArtistDetailActivityViewModel
import com.sunilk.tattoo.ui.activity.search.SearchActivity
import com.sunilk.tattoo.ui.activity.search.SearchActivityViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Sunil on 10/1/18.
 */

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(spectreApplication: SpectreApplication)

    fun inject(searchActivity: SearchActivity)

    fun inject(artistDetailActivity: ArtistDetailActivity)

    fun inject(searchActivityViewModel: SearchActivityViewModel)

    fun inject(artistDetailActivityViewModel: ArtistDetailActivityViewModel)

}