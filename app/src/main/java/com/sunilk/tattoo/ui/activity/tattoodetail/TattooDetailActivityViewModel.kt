package com.sunilk.tattoo.ui.activity.tattoodetail

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import com.sunilk.tattoo.network.INetworkService
import com.sunilk.tattoo.network.api.artist.TattooDetailResponse
import com.sunilk.tattoo.network.api.search.Artist
import com.sunilk.tattoo.network.api.search.Shop
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Sunil on 21/10/18.
 */

class TattooDetailActivityViewModel {

    companion object {

        val TAG: String = TattooDetailActivityViewModel::class.java.simpleName
    }

    private var tattooId: String? = null
    private var tattooDetailActivityNavigator: ITattooDetailActivityNavigator
    private val networkService: INetworkService

    private var disposable = CompositeDisposable()
    private var artistDetailDisposable: Disposable? = null

    val showProgress = ObservableBoolean(true)

    val artistName = ObservableField<String>("")
    val userName = ObservableField<String>("")
    val tattooImageUrl = ObservableField<String>("")
    val profileImageUrl = ObservableField<String>("")

    val shopName = ObservableField<String>("")
    val shopAddress = ObservableField<String>("")

    val noOfPosts = ObservableField<String>("0")
    val noOfFollowers = ObservableField<String>("0")

    val hashtags = ObservableField<String>("")

    private var shouldRetryApiCall = false

    constructor(
        tattooId: String?, tattooDetailActivityNavigator: ITattooDetailActivityNavigator,
        networkService: INetworkService
    ) {

        this.tattooId = tattooId
        this.tattooDetailActivityNavigator = tattooDetailActivityNavigator
        this.networkService = networkService

        setUpViewModel()
    }

    private fun setUpViewModel() {

        getTattooDetail(tattooId)
    }

    private fun getTattooDetail(artistId: String?) {

        artistDetailDisposable?.dispose()

        if (artistId != null && !artistId.isEmpty()) {

            showProgress.set(true)

            artistDetailDisposable = networkService.getTattooDetailFlowable(artistId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ artistDetailResponse ->

                    handleArtistDetailResponse(artistDetailResponse)

                }, { e ->

                    Log.e(TAG, "Error : $e")
                    handleArtistDetailFailed()
                })

            artistDetailDisposable?.let { disposable.add(it) }

        } else {

            showProgress.set(false)
        }
    }

    private fun handleArtistDetailResponse(tattooDetailResponse: TattooDetailResponse) {

        showProgress.set(false)

        tattooDetailResponse.tattooDetail?.apply {

            if (artist != null) {
                setArtistDetail(artist)
            }

            tattooImageUrl.set(image?.url)

            if (shop != null) {
                setShopDetails(shop)
            }

            val sb = StringBuilder()
            classification?.hashtags?.forEach { hashtag ->
                sb.append("#$hashtag ")
            }

            hashtags.set(sb.toString())

        }

        tattooDetailActivityNavigator.animateTextDetails()
    }

    private fun setArtistDetail(artist: Artist) {

        artistName.set(artist.name)
        userName.set("@" + artist.username)
        profileImageUrl.set(artist.image_url)


        noOfPosts.set(artist.counts?.posts ?: "0")
        noOfFollowers.set(artist.counts?.followers ?: "0")
    }


    private fun setShopDetails(shop: Shop) {

        shopName.set(shop.name ?: "")

        val address = shop.address?.address1 ?: ""+shop.address?.country ?: ""
        shopAddress.set(address)
    }

    private fun handleArtistDetailFailed() {

        showProgress.set(false)

        shouldRetryApiCall = true
        tattooDetailActivityNavigator.showError()
    }


    fun onCloseButtonClick() = {

        tattooDetailActivityNavigator.closeArtistDetail()
    }

    fun onDestroy() {

        disposable.dispose()
    }
}
