package com.sunilk.tattoo.ui.activity.search.viewmodels

import com.sunilk.tattoo.network.api.models.TattooDetail
import com.sunilk.tattoo.ui.adapter.ViewModel
import android.databinding.ObservableField

/**
 * Created by Sunil on 20/10/18.
 */

class TattooSearchItemViewModel : ViewModel {

    val albumImageUrl = ObservableField<String>("")

    private var onClickAction: ((String) -> Unit)?
    private val tattooDetail: TattooDetail

    constructor(tattooDetail: TattooDetail, onClickAction: ((String) -> Unit)? = null) {

        this.tattooDetail = tattooDetail
        this.onClickAction = onClickAction

        setUpViewModel()
    }

    private fun setUpViewModel() {

        tattooDetail.apply {

            albumImageUrl.set(image?.url)
        }
    }

    fun onClick() = {

        tattooDetail.id?.let { id -> onClickAction?.invoke(id) }
    }
}