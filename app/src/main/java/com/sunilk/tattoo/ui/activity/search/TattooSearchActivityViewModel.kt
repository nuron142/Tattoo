package com.sunilk.tattoo.ui.activity.search

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import com.sunilk.tattoo.R
import com.sunilk.tattoo.network.INetworkService
import com.sunilk.tattoo.network.api.search.TattooSearchResponse
import com.sunilk.tattoo.ui.activity.search.viewmodels.SearchTattooViewModel
import com.sunilk.tattoo.ui.adapter.ViewModel
import com.sunilk.tattoo.util.toFlowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Sunil on 20/10/18.
 */
class TattooSearchActivityViewModel {

    companion object {

        val TAG: String = TattooSearchActivityViewModel::class.java.simpleName
    }

    val networkService: INetworkService

    var dataSet = ObservableArrayList<ViewModel>()

    val viewModelLayoutIdMap: HashMap<Class<out ViewModel>, Int> = hashMapOf(
        SearchTattooViewModel::class.java to R.layout.item_search_tattoo_layout
    )

    private var disposable = CompositeDisposable()
    private var searchDisposable: Disposable? = null

    val showCloseButton = ObservableBoolean(false)
    private val tattooSearchActivityNavigator: ITattooSearchActivityNavigator

    val showProgress = ObservableBoolean(false)
    var searchQuery = ObservableField<String>("")

    private var shouldRetryApiCall = false

    constructor(tattooSearchActivityNavigator: ITattooSearchActivityNavigator, networkService: INetworkService) {

        this.tattooSearchActivityNavigator = tattooSearchActivityNavigator
        this.networkService = networkService

        setUpViewModel()
    }

    private fun setUpViewModel() {

        disposable.add(searchQuery.toFlowable()
            .map { query ->

                showProgress.set(query.isNotEmpty())
                showCloseButton.set(query.isNotEmpty())

                return@map query
            }
            .debounce(600, TimeUnit.MILLISECONDS)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ query ->
                getSearchList(query)
            }, { e -> Log.d(TAG, "" + e.message) })
        )


        searchQuery.set("Dotwork")
    }

    fun getRandomTattooList() {

    }

    private fun getSearchList(query: String?) {

        searchDisposable?.dispose()

        if (query != null && !query.isEmpty()) {

            showProgress.set(true)
            showCloseButton.set(true)

            searchDisposable = networkService.getSearchQueryFlowable(query.toLowerCase())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ searchResponse ->

                    handleSearchResponse(searchResponse)

                }, { e ->

                    Log.d(TAG, "Error : $e")
                    handleSearchFailed()
                })

        } else {

            dataSet.clear()
            showProgress.set(false)
            showCloseButton.set(false)
        }
    }

    private fun handleSearchFailed() {

        dataSet.clear()

        showProgress.set(false)
        tattooSearchActivityNavigator.showError()

        shouldRetryApiCall = true
    }


    private fun handleSearchResponse(tattooSearchResponse: TattooSearchResponse?) {

        tattooSearchResponse?.apply {

            showProgress.set(false)
            dataSet.clear()

            tattooList?.forEach { tattooDetail ->

                val searchArtistViewModel = SearchTattooViewModel(tattooDetail) { tattooId ->
                    tattooSearchActivityNavigator.openTattooDetailPage(tattooId)
                }
                dataSet.add(searchArtistViewModel)
            }

            if (dataSet.size == 0) {
                tattooSearchActivityNavigator.showNoResultsFound()
            }
        }
    }

    fun onCancelButtonCLick() = {

        dataSet.clear()

        searchQuery.set("")
        showProgress.set(false)
        showCloseButton.set(false)
    }

    fun onDestroy() {

        disposable.dispose()
    }
}
