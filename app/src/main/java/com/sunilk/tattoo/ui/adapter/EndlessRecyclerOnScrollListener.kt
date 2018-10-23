package com.sunilk.tattoo.ui.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by Sunil on 23/10/18.
 */

abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener {

    companion object {

        val TAG = EndlessRecyclerOnScrollListener::class.java.simpleName
    }

    // The total number of items in the dataset after the last load
    private var previousTotal = 0

    // True if we are still waiting for the last set of data to load.
    private var isLoading = true

    // The minimum amount of items to have below your current
    // scroll position before onLoadNextPage is called.
    private var visibleThreshold = 0

    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0

    private var pageNumber = 0

    private var mLinearLayoutManager: LinearLayoutManager? = null

    constructor(linearLayoutManager: LinearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager
    }

    constructor(linearLayoutManager: LinearLayoutManager, visibleThreshold: Int) {
        this.mLinearLayoutManager = linearLayoutManager
        this.visibleThreshold = visibleThreshold
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = mLinearLayoutManager!!.itemCount
        firstVisibleItem = mLinearLayoutManager!!.findFirstVisibleItemPosition()

        if (isLoading) {
            if (totalItemCount > previousTotal) {
                isLoading = false
                previousTotal = totalItemCount
            }
        }
        if (!isLoading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {

            pageNumber++

            onLoadNextPage(pageNumber)

            isLoading = true
        }
    }

    abstract fun onLoadNextPage(currentPage: Int)

    fun resetListener() {

        visibleItemCount = 0
        totalItemCount = 0
        firstVisibleItem = 0
        isLoading = true
        previousTotal = 0
        pageNumber = 0
    }
}
