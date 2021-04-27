package de.sixbits.pixaclient.unit.network.manager

import de.sixbits.pixaclient.BuildConfig
import de.sixbits.pixaclient.PixabayResponseFactory
import de.sixbits.pixaclient.network.manager.PixabayManager
import de.sixbits.pixaclient.network.service.PixabayService
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock


@RunWith(JUnit4::class)
class TestPixabayManager {

    lateinit var pixabayManager: PixabayManager
    lateinit var pixabayService: PixabayService

    @Before
    fun setup() {
        // Prepairing RX
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

        // Mocking the API
        pixabayService = mock(PixabayService::class.java)
        pixabayManager = PixabayManager(pixabayService)
    }

    @Test
    fun testGetSearchResult() {
        val searchQuery = "fruits"

        // region Test Good Response
        Mockito
            .`when`(pixabayService.getSearchResult(searchQuery, BuildConfig.PIXABAY_KEY))
            .thenReturn(Observable.just(PixabayResponseFactory.getResponse()))

        pixabayManager.getSearchResult(searchQuery)
            .test()
            .assertValue { it.size == 1 }

        // endregion

        // region Test with Null Response
        Mockito
            .`when`(pixabayService.getSearchResult(searchQuery, BuildConfig.PIXABAY_KEY))
            .thenReturn(Observable.just(PixabayResponseFactory.getZeroResponse()))

        pixabayManager.getSearchResult(searchQuery)
            .test()
            .assertValue {
                it.isEmpty()
            }
        // endregion

        // region Test with exceptions
        Mockito
            .`when`(pixabayService.getSearchResult(searchQuery, BuildConfig.PIXABAY_KEY))
            .thenReturn(Observable.error(Exception("Bad Response")))

        pixabayManager.getSearchResult(searchQuery)
            .test()
            .assertError {
                true
            }
        // endregion
    }
}