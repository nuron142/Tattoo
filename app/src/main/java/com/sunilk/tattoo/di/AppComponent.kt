package com.sunilk.tattoo.di

import com.sunilk.tattoo.ui.TattooApplication
import com.sunilk.tattoo.ui.activity.tattoodetail.TattooDetailActivity
import com.sunilk.tattoo.ui.activity.tattoodetail.TattooDetailActivityViewModel
import com.sunilk.tattoo.ui.activity.search.TattooSearchActivity
import com.sunilk.tattoo.ui.activity.search.TattooSearchActivityViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Sunil on 20/10/18.
 */

@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: TattooApplication): Builder

        fun build(): AppComponent
    }

    fun inject(tattooApplication: TattooApplication)

    fun inject(tattooSearchActivity: TattooSearchActivity)

    fun inject(tattooDetailActivity: TattooDetailActivity)

    fun inject(tattooSearchActivityViewModel: TattooSearchActivityViewModel)

    fun inject(tattooDetailActivityViewModel: TattooDetailActivityViewModel)

}