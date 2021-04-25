package de.sixbits.pixaclient.unit.main.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import de.sixbits.pixaclient.ImageResponseFactory
import de.sixbits.pixaclient.database.dao.CacheDao
import de.sixbits.pixaclient.database.utils.ImageEntityMapper
import de.sixbits.pixaclient.main.repository.DetailsRepository
import de.sixbits.pixaclient.network.manager.PixabayManager
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito
import org.mockito.kotlin.any


@RunWith(JUnit4::class)
class TestDetailsRepository {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // Prepairing RX
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun testGetImageDetails() {

        val cacheDao = Mockito.mock(CacheDao::class.java)
        val pixabayManager = Mockito.mock(PixabayManager::class.java)

        // Test when everything is correct
        val detailsItem = ImageResponseFactory.getImageDetails()

        Mockito.`when`(pixabayManager.getImageDetails(anyInt()))
            .thenReturn(Observable.just(detailsItem))
        Mockito.`when`(cacheDao.insert(any()))
            .thenReturn(Completable.complete())

        var detailsRepository = DetailsRepository(
            cacheDao = cacheDao,
            pixabayManager = pixabayManager
        )

        detailsRepository.getImageDetails(123)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                assert(detailsItem.id == response.id)
            }, {
                throw Exception(it.message)
            })

        // Test when a network error
        Mockito.`when`(pixabayManager.getImageDetails(123))
            .thenReturn(Observable.error(Exception("No Response")))
        Mockito.`when`(cacheDao.insert(ImageEntityMapper.fromImageDetailsModel(detailsItem)))
            .thenReturn(Completable.complete())

        detailsRepository =
            DetailsRepository(cacheDao = cacheDao, pixabayManager = pixabayManager)

        detailsRepository.getImageDetails(123)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                assert(false)
            }, {
                assert(it.message == "No Response")
            })
    }
}