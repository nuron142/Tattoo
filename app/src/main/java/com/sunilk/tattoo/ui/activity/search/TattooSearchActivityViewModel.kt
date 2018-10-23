package com.sunilk.tattoo.ui.activity.search

import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.Log
import com.sunilk.tattoo.R
import com.sunilk.tattoo.network.IRepositoryService
import com.sunilk.tattoo.network.api.response.TattooSearchResponse
import com.sunilk.tattoo.ui.activity.search.viewmodels.SearchTattooViewModel
import com.sunilk.tattoo.ui.adapter.ViewModel
import com.sunilk.tattoo.util.toFlowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Sunil on 20/10/18.
 */
class TattooSearchActivityViewModel
@Inject
constructor(val repositoryService: IRepositoryService) {

    companion object {

        val TAG: String = TattooSearchActivityViewModel::class.java.simpleName
    }

    var dataSet = ObservableArrayList<ViewModel>()

    val viewModelLayoutIdMap: HashMap<Class<out ViewModel>, Int> = hashMapOf(
        SearchTattooViewModel::class.java to R.layout.item_search_tattoo_layout
    )

    private var disposable = CompositeDisposable()
    private var searchDisposable: Disposable? = null

    val showCloseButton = ObservableBoolean(false)

    val showProgress = ObservableBoolean(false)
    var searchQuery = ObservableField<String>("")

    val showNoResultsSubject = PublishProcessor.create<Boolean>()
    val showErrorSubject = PublishProcessor.create<Boolean>()
    val openTattooPageSubject = PublishProcessor.create<String>()

    init {

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

        getRandomTattooList()
    }

    fun getRandomTattooList() {

        searchQuery.set("Dotwork")
    }

    private fun getSearchList(query: String?) {

        searchDisposable?.dispose()

        if (query != null && !query.isEmpty()) {

            showProgress.set(true)
            showCloseButton.set(true)

            searchDisposable = repositoryService.getSearchQueryFlowable(query.toLowerCase())
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
        showErrorSubject.offer(true)
    }


    private fun handleSearchResponse(tattooSearchResponse: TattooSearchResponse?) {

        tattooSearchResponse?.apply {

            showProgress.set(false)
            dataSet.clear()

            tattooList?.forEach { tattooDetail ->

                val searchArtistViewModel = SearchTattooViewModel(tattooDetail) { tattooId ->
                    openTattooPageSubject.offer(tattooId)
                }

                dataSet.add(searchArtistViewModel)
            }

            if (dataSet.size == 0) {
                showNoResultsSubject.offer(true)
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
