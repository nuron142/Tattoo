package com.sunilk.tattoo.ui.activity.search

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import com.sunilk.tattoo.R
import com.sunilk.tattoo.databinding.ActivityTattooSearchBinding
import com.sunilk.tattoo.ui.activity.tattoodetail.TattooDetailActivity
import com.sunilk.tattoo.ui.adapter.BindingRecyclerAdapter
import com.sunilk.tattoo.ui.adapter.EndlessRecyclerOnScrollListener
import com.sunilk.tattoo.ui.adapter.TattooSearchGridDecoration
import com.sunilk.tattoo.util.Utilities
import com.sunilk.tattoo.util.itemanimators.AlphaCrossFadeAnimator
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


/**
 * Created by Sunil on 20/10/18.
 */
class TattooSearchActivity : DaggerAppCompatActivity() {

    companion object {

        val TAG: String = TattooSearchActivity::class.java.simpleName
    }

    @Inject
    lateinit var tattooSearchActivityViewModel: TattooSearchActivityViewModel

    private lateinit var binding: ActivityTattooSearchBinding

    private val allSubscriptions = CompositeDisposable()

    private var endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_tattoo_search)

        binding.vm = tattooSearchActivityViewModel
        binding.executePendingBindings()

        subscribeToViewModel()

        setupRecyclerView()

        Utilities.showKeyBoard(this, binding.searchEditText)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupRecyclerView() {

        val itemAnimator = AlphaCrossFadeAnimator()

        itemAnimator.addDuration = 200
        itemAnimator.removeDuration = 200
        itemAnimator.changeDuration = 200
        itemAnimator.moveDuration = 200

        val gridLayoutManager = GridLayoutManager(this, 2)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {

                return if ((position + 1) % 5 == 0) 2 else 1
            }
        }

        binding.recyclerView.layoutManager = gridLayoutManager
        val adapter = BindingRecyclerAdapter(
            tattooSearchActivityViewModel.dataSet,
            tattooSearchActivityViewModel.viewModelLayoutIdMap
        )

        binding.recyclerView.addItemDecoration(
            TattooSearchGridDecoration(resources.getDimensionPixelSize(R.dimen.itemdecoratoroffset))
        )

        endlessRecyclerOnScrollListener = object : EndlessRecyclerOnScrollListener(gridLayoutManager, 4) {
            override fun onLoadNextPage(currentPage: Int) {
                tattooSearchActivityViewModel.loadMoreItems(currentPage + 1)
            }
        }

        endlessRecyclerOnScrollListener?.let { listener -> binding.recyclerView.addOnScrollListener(listener) }

        binding.recyclerView.itemAnimator = itemAnimator
        binding.recyclerView.adapter = adapter

        binding.recyclerView.setOnTouchListener { v, e ->

            Utilities.hideKeyBoard(this, binding.recyclerView)
            return@setOnTouchListener false
        }
    }

    private fun subscribeToViewModel() {

        allSubscriptions.add(
            tattooSearchActivityViewModel.openTattooPageSubject.hide()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ tattooId ->

                    TattooDetailActivity.launch(this@TattooSearchActivity, tattooId)
                }, { e -> Log.e(TAG, "Error : " + e.message) })
        )

        allSubscriptions.add(
            tattooSearchActivityViewModel.showErrorSubject.hide()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    Snackbar.make(binding.root, getString(R.string.general_error), Snackbar.LENGTH_SHORT).show()
                }, { e -> Log.e(TAG, "Error : " + e.message) })
        )

        allSubscriptions.add(
            tattooSearchActivityViewModel.showNoResultsSubject.hide()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    Snackbar.make(binding.root, getString(R.string.no_results_found), Snackbar.LENGTH_SHORT).show()
                }, { e -> Log.e(TAG, "Error : " + e.message) })
        )

        allSubscriptions.add(
            tattooSearchActivityViewModel.resetScrollCountSubject.hide()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    endlessRecyclerOnScrollListener?.resetListener()

                }, { e -> Log.e(TAG, "Error : " + e.message) })
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        allSubscriptions.dispose()
        tattooSearchActivityViewModel.onDestroy()

    }
}
