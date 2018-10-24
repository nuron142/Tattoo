package com.sunilk.tattoo

import com.nhaarman.mockitokotlin2.mock
import com.sunilk.tattoo.network.IRepositoryService
import com.sunilk.tattoo.network.api.response.TattooDetailResponse
import com.sunilk.tattoo.ui.activity.tattoodetail.TattooDetailActivityViewModel
import com.sunilk.tattoo.util.Utilities
import io.reactivex.subscribers.TestSubscriber
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test


/**
 * Created by Sunil on 24/10/18.
 */

class TattooDetailViewModelTest {

    private lateinit var mockRepositoryService: IRepositoryService

    @Before
    fun setup() {

        mockRepositoryService = mock()
    }

    @Test
    fun viewmodel_emptyTattooId_test() {

        val viewModel = TattooDetailActivityViewModel("", mockRepositoryService)

        val testSubscriber = TestSubscriber<Boolean>()
        viewModel.showErrorSubject.hide().subscribe(testSubscriber)

        viewModel.setUpViewModel()
        testSubscriber.assertValue(true)
    }

    @Test
    fun viewmodel_actualResponse_test() {

        val tattooDetailResponse = Utilities.getSampleResponseData(
            TattooDetailResponse::class.java, "tattooDetailResponse.json"
        )

        val viewModel = TattooDetailActivityViewModel("45675", mockRepositoryService)

        viewModel.handleArtistDetailResponse(tattooDetailResponse)

        assertEquals(viewModel.artistName.get(), "Joe Allison")
        assertNotEquals(viewModel.userName.get(), "Darth Vader")
        assertNotEquals(viewModel.tattooImageUrl.get(), "")
        assertEquals(viewModel.artistNameDetailVisibility.get(), true)
    }

    @Test
    fun viewmodel_emptyResponse_test() {

        val viewModel = TattooDetailActivityViewModel("45675", mockRepositoryService)

        val testSubscriber = TestSubscriber<Boolean>()
        viewModel.showErrorSubject.hide().subscribe(testSubscriber)

        viewModel.handleArtistDetailResponse(null)

        assertEquals(viewModel.artistName.get(), "")
        assertEquals(viewModel.userName.get(), "")
        assertEquals(viewModel.tattooImageUrl.get(), "")
        testSubscriber.assertValue(true)

        assertEquals(viewModel.showProgress.get(), false)
    }

    @Test
    fun viewmodel_animateDetails_test() {

        val viewModel = TattooDetailActivityViewModel("45675", mockRepositoryService)

        val testSubscriberShowError = TestSubscriber<Boolean>()
        viewModel.showErrorSubject.hide().subscribe(testSubscriberShowError)

        val testSubscriberAnimateTextDetails = TestSubscriber<Boolean>()
        viewModel.animateTextDetailsSubject.hide().subscribe(testSubscriberAnimateTextDetails)

        val tattooDetailResponse = Utilities.getSampleResponseData(
            TattooDetailResponse::class.java, "tattooDetailResponse.json"
        )

        viewModel.handleArtistDetailResponse(tattooDetailResponse)

        assertEquals(viewModel.artistName.get(), "Joe Allison")
        assertEquals(viewModel.noOfPosts.get(), "30")
        assertNotEquals(viewModel.hashtags.get(), "")
        assertNotEquals(viewModel.shopAddress.get(), "")
        assertEquals(viewModel.showProgress.get(), false)

        testSubscriberShowError.assertNoValues()
        testSubscriberAnimateTextDetails.assertValue(true)
    }

    @Test
    fun viewmodel_closeDetailPage_test() {

        val viewModel = TattooDetailActivityViewModel("", mockRepositoryService)

        val testSubscriber = TestSubscriber<Boolean>()
        viewModel.closeArtistDetailSubject.hide().subscribe(testSubscriber)

        viewModel.onCloseButtonClick().invoke()
        testSubscriber.assertValue(true)
    }
}
