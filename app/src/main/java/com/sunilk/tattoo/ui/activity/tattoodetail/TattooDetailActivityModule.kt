package com.sunilk.tattoo.ui.activity.tattoodetail

import dagger.Module
import dagger.Provides

/**
 * Created by Sunil on 21/10/18.
 */

@Module
object TattooDetailActivityModule {

    @Provides
    @JvmStatic
    fun providesTattooId(tattooDetailActivity: TattooDetailActivity): String? {
        return tattooDetailActivity.intent?.getStringExtra(TattooDetailActivity.TATTOO_ID)
    }
}