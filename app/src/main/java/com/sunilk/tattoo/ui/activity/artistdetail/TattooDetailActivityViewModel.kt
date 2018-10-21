package com.sunilk.tattoo.ui.activity.artistdetail

import com.sunilk.tattoo.network.INetworkService
import com.sunilk.tattoo.network.api.artist.TattooDetailResponse
import com.sunilk.tattoo.ui.adapter.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Sunil on 21/10/18.
 */

class TattooDetailActivityViewModel {

    companion object {

        val TAG: String = TattooDetailActivityViewModel::class.java.simpleName

    }

    @Inject
    lateinit var networkService: INetworkService

    private var tattooId: String? = null
    private var tattooDetailActivityService: ITattooDetailActivityService
    private var disposable = CompositeDisposable()
    private var artistDetailDisposable: Disposable? = null

    val showProgress = ObservableBoolean(true)

    val artistName = ObservableField<String>("")
    val genres = ObservableField<String>("")
    val albumReleaseDate = ObservableField<String>("")
    val artistImageUrl = ObservableField<String>("")

    val showTopTracks = ObservableBoolean(false)

    var dataSet = ObservableArrayList<ViewModel>()

    private var shouldRetryApiCall = false

    constructor(tattooId: String?, tattooDetailActivityService: ITattooDetailActivityService) {

        this.tattooId = tattooId
        this.tattooDetailActivityService = tattooDetailActivityService
    }

    fun init() {

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

                        handleArtistDetailResponse(artistId, artistDetailResponse)

                    }, { e ->

                        Log.e(TAG, "Error : $e")
                        handleArtistDetailFailed()
                    })

            artistDetailDisposable?.let { disposable.add(it) }

        } else {

            showProgress.set(false)
        }
    }

    private fun handleArtistDetailResponse(artistId: String, tattooDetailResponse: TattooDetailResponse) {

        showProgress.set(false)

        dataSet.clear()

        artistName.set(tattooDetailResponse.tattooDetail?.artist?.name)
        artistImageUrl.set(tattooDetailResponse.tattooDetail?.image?.url)
    }

    private fun handleArtistDetailFailed() {

        showProgress.set(false)

        shouldRetryApiCall = true
        tattooDetailActivityService.showError()
    }


    fun onCloseButtonClick() = {

        tattooDetailActivityService.closeArtistDetail()
    }

    fun onDestroy() {

        disposable.dispose()
    }
}
