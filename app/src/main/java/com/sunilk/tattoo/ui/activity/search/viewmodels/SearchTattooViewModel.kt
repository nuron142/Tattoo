package com.sunilk.tattoo.ui.activity.search.viewmodels

import com.sunilk.tattoo.network.api.search.TattooDetail
import com.sunilk.tattoo.ui.adapter.ViewModel
import android.databinding.ObservableField
import java.text.SimpleDateFormat

/**
 * Created by Sunil on 10/5/18.
 */
class SearchTattooViewModel : ViewModel {

    val name = ObservableField<String>("")

    val albumName = ObservableField<String>("")
    val albumArtists = ObservableField<String>("")
    val albumReleaseDate = ObservableField<String>("")
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

            albumName.set(artist?.name)

            albumImageUrl.set(image?.url)
        }

    }

    fun onClick() = {

        tattooDetail.id?.let { id -> onClickAction?.invoke(id) }
    }
}