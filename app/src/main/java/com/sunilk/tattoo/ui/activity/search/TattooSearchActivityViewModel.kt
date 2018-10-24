package com.sunilk.tattoo.ui.activity.search

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import com.sunilk.tattoo.R
import com.sunilk.tattoo.network.IRepositoryService
import com.sunilk.tattoo.network.api.response.TattooSearchResponse
import com.sunilk.tattoo.ui.activity.search.viewmodels.TattooSearchItemViewModel
import com.sunilk.tattoo.ui.adapter.ViewModel
import com.sunilk.tattoo.util.toFlowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Sunil on 20/10/18.
 */

open class TattooSearchActivityViewModel
@Inject
constructor(val repositoryService: IRepositoryService) {

    companion object {

        val TAG: String = TattooSearchActivityViewModel::class.java.simpleName
    }

    var dataSet = ObservableArrayList<ViewModel>()

    val viewModelLayoutIdMap: HashMap<Class<out ViewModel>, Int> = hashMapOf(
        TattooSearchItemViewModel::class.java to R.layout.item_search_tattoo_layout
    )

    private val searchKeywords = listOf("Geometric", "Space", "Minimal", "Nature", "Ocean")

    private var disposable = CompositeDisposable()
    private var searchDisposable: Disposable? = null

    val showClearButton = ObservableBoolean(false)

    val showProgress = ObservableBoolean(false)
    var searchQuery = ObservableField<String>("")

    val showNoResultsSubject = PublishProcessor.create<Boolean>()
    val showErrorSubject = PublishProcessor.create<Boolean>()
    val openTattooPageSubject = PublishProcessor.create<String>()
    val resetScrollCountSubject = PublishProcessor.create<Boolean>()

    private var currentTotalPages = 0

    fun setUpViewModel() {

        disposable.add(searchQuery.toFlowable()
            .map { query ->

                showProgress.set(query.isNotEmpty())
                showClearButton.set(query.isNotEmpty())

                return@map query
            }
            .debounce(600, TimeUnit.MILLISECONDS)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ query ->
                getSearchList(query, null)
            }, { e -> Log.d(TAG, "" + e.message) })
        )

        getRandomTattooList()
    }

    //Loading a random tattoo list at startup
    private fun getRandomTattooList() {

        searchQuery.set(searchKeywords.get(Random().nextInt(searchKeywords.size)))
    }


    fun loadMoreItems(page: Int) {

        if (page <= currentTotalPages) {
            getSearchList(searchQuery.get(), page)
        }
    }

    private fun getSearchList(query: String?, page: Int?) {

        searchDisposable?.dispose()

        if (query != null && !query.isEmpty()) {

            showProgress.set(true)
            showClearButton.set(true)

            searchDisposable = repositoryService.getSearchQuery(query.toLowerCase(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ searchResponse ->

                    handleSearchResponse(searchResponse, page)

                }, { e ->

                    Log.d(TAG, "Error : $e")
                    handleSearchFailed()
                })

        } else {

            clearDataSet()
            showProgress.set(false)
            showClearButton.set(false)
        }
    }

    private fun handleSearchFailed() {

        dataSet.clear()

        showProgress.set(false)
        showErrorSubject.offer(true)
    }

    private fun clearDataSet() {

        dataSet.clear()
        resetScrollCountSubject.offer(true)

        currentTotalPages = 0
    }

    internal fun handleSearchResponse(tattooSearchResponse: TattooSearchResponse?, page: Int?) {

        showProgress.set(false)

        if (tattooSearchResponse != null) {

            if (page == null || page == 0) {
                clearDataSet()
            }

            tattooSearchResponse.tattooList?.forEach { tattooDetail ->

                val searchArtistViewModel = TattooSearchItemViewModel(tattooDetail) { tattooId ->
                    openTattooPageSubject.offer(tattooId)
                }

                dataSet.add(searchArtistViewModel)
            }

            currentTotalPages = tattooSearchResponse.meta?.pagination?.total_pages ?: 0

            if (dataSet.size == 0) {
                showNoResultsError()
            }

        } else {

            showNoResultsError()
        }
    }

    private fun showNoResultsError() {

        clearDataSet()
        showNoResultsSubject.offer(true)
    }

    fun onClearButtonCLick() = {

        clearDataSet()

        searchQuery.set("")
        showProgress.set(false)
        showClearButton.set(false)
    }

    fun onDestroy() {

        disposable.dispose()
    }
}
