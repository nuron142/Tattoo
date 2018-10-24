package com.sunilk.tattoo

import com.nhaarman.mockitokotlin2.mock
import com.sunilk.tattoo.network.IRepositoryService
import com.sunilk.tattoo.network.api.response.TattooSearchResponse
import com.sunilk.tattoo.ui.activity.search.TattooSearchActivityViewModel
import com.sunilk.tattoo.ui.activity.search.viewmodels.TattooSearchItemViewModel
import com.sunilk.tattoo.util.Utilities
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Sunil on 24/10/18.
 */

class TattooSearchViewModelTest {

    private lateinit var mockRepositoryService: IRepositoryService

    @Before
    fun setup() {

        mockRepositoryService = mock()
    }

    @Test
    fun viewmodel_clearButton_test() {

        val viewModel = TattooSearchActivityViewModel(mockRepositoryService)

        viewModel.searchQuery.set("Hello")
        assertEquals(viewModel.searchQuery.get(), "Hello")

        viewModel.onClearButtonCLick().invoke()

        assertEquals(viewModel.searchQuery.get(), "")
        assertEquals(viewModel.showProgress.get(), false)
    }


    @Test
    fun viewmodel_emptyResponse_test() {

        val viewModel = TattooSearchActivityViewModel(mockRepositoryService)

        val testSubscriber = TestSubscriber<Boolean>()
        viewModel.showNoResultsSubject.hide().subscribe(testSubscriber)

        viewModel.handleSearchResponse(null, null)
        assertEquals(viewModel.showProgress.get(), false)
        assertEquals(viewModel.dataSet.size, 0)
        testSubscriber.assertValue(true)
    }

    @Test
    fun viewmodel_actualResponse_test() {

        val viewModel = TattooSearchActivityViewModel(mockRepositoryService)

        val testSubscriber = TestSubscriber<Boolean>()
        viewModel.showNoResultsSubject.hide().subscribe(testSubscriber)

        val tattooSearchResponse = Utilities.getSampleResponseData(
            TattooSearchResponse::class.java, "tattooSearchResponse.json"
        )
        viewModel.handleSearchResponse(tattooSearchResponse, null)
        assertEquals(viewModel.showProgress.get(), false)
        assertEquals(viewModel.dataSet.size, 2)
        testSubscriber.assertNoValues()
    }

    @Test
    fun viewmodel_openTattoPage_test() {

        val viewModel = TattooSearchActivityViewModel(mockRepositoryService)

        val testSubscriber = TestSubscriber<String>()
        viewModel.openTattooPageSubject.hide().subscribe(testSubscriber)

        val tattooSearchResponse = Utilities.getSampleResponseData(
            TattooSearchResponse::class.java, "tattooSearchResponse.json"
        )
        viewModel.handleSearchResponse(tattooSearchResponse, null)
        assertEquals(viewModel.dataSet.size, 2)

        (viewModel.dataSet[0] as TattooSearchItemViewModel).onClick().invoke()
        testSubscriber.assertValue("737290")
    }
}
