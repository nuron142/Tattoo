package com.sunilk.tattoo.ui.activity.tattoodetail

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import com.sunilk.tattoo.network.IRepositoryService
import com.sunilk.tattoo.network.api.models.Artist
import com.sunilk.tattoo.network.api.models.Shop
import com.sunilk.tattoo.network.api.response.TattooDetailResponse
import com.sunilk.tattoo.util.isNotEmpty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Sunil on 21/10/18.
 */

open class TattooDetailActivityViewModel
@Inject
constructor(
    private val tattooId: String?,
    private val repositoryService: IRepositoryService
) {

    companion object {

        val TAG: String = TattooDetailActivityViewModel::class.java.simpleName
    }

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

    val artistNameDetailVisibility = ObservableBoolean(true)

    val animateTextDetailsSubject = PublishProcessor.create<Boolean>()
    val showErrorSubject = PublishProcessor.create<Boolean>()
    val closeArtistDetailSubject = PublishProcessor.create<Boolean>()


    fun setUpViewModel() {

        getTattooDetail(tattooId)
    }

    private fun getTattooDetail(artistId: String?) {

        artistDetailDisposable?.dispose()

        if (artistId != null && !artistId.isEmpty()) {

            showProgress.set(true)

            artistDetailDisposable = repositoryService.getTattooDetail(artistId)
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

            handleArtistDetailFailed()
        }
    }

    internal fun handleArtistDetailResponse(tattooDetailResponse: TattooDetailResponse?) {

        showProgress.set(false)

        if (tattooDetailResponse?.tattooDetail != null) {

            tattooDetailResponse.tattooDetail.apply {

                artistNameDetailVisibility.set(artist != null)
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

            animateTextDetailsSubject.offer(true)

        } else {

            handleArtistDetailFailed()
        }
    }

    private fun setArtistDetail(artist: Artist) {

        artist.name?.isNotEmpty { name -> artistName.set(name) }
        artist.username?.isNotEmpty { username -> userName.set("@$username") }
        artist.image_url?.isNotEmpty { url -> profileImageUrl.set(url) }

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
        showErrorSubject.offer(true)
    }

    fun onCloseButtonClick(): () -> Unit {

        return {

            closeArtistDetailSubject.offer(true)
        }
    }

    fun onDestroy() {

        disposable.dispose()
    }
}
