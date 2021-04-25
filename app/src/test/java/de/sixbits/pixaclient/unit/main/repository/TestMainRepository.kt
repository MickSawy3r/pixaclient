package de.sixbits.pixaclient.unit.main.repository

import de.sixbits.pixaclient.ImageEntityFactory
import de.sixbits.pixaclient.ImageResponseFactory
import de.sixbits.pixaclient.database.dao.CacheDao
import de.sixbits.pixaclient.main.repository.MainRepository
import de.sixbits.pixaclient.network.manager.PixabayManager
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

@RunWith(JUnit4::class)
class TestMainRepository {

    @Before
    fun setUp() {
        // Preparing RX
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun testGetCached() {
        val cacheDao = Mockito.mock(CacheDao::class.java)
        val pixabayManager = Mockito.mock(PixabayManager::class.java)

        val expectedCache = listOf(ImageEntityFactory.getImageItem1())

        Mockito.`when`(cacheDao.getAll())
            .thenReturn(Observable.just(expectedCache))

        val mainRepository = MainRepository(cacheDao = cacheDao, pixabayManager = pixabayManager)

        mainRepository.getCached()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                assert(it.size == 1)
                assert(it[0].id == expectedCache[0].id)
            }
    }

    @Test
    fun testSearchFor() {
        val cacheDao = Mockito.mock(CacheDao::class.java)
        val pixabayManager = Mockito.mock(PixabayManager::class.java)

        val expectedCache = listOf(ImageResponseFactory.getImageListItem())

        Mockito.`when`(pixabayManager.getSearchResult(anyString()))
            .thenReturn(Observable.just(expectedCache))

        val mainRepository = MainRepository(cacheDao = cacheDao, pixabayManager = pixabayManager)

        mainRepository.searchFor("Query")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                assert(it.size == 1)
                assert(it[0].id == expectedCache[0].id)
            }
    }
}