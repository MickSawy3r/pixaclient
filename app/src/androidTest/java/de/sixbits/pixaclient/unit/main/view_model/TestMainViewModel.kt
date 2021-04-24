package de.sixbits.pixaclient.unit.main.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import de.sixbits.pixaclient.main.repository.MainRepository
import de.sixbits.pixaclient.main.view_model.MainViewModel
import de.sixbits.pixaclient.unit.TestMyApplication
import de.sixbits.pixaclient.unit.di.DaggerTestAppComponent
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(AndroidJUnit4ClassRunner::class)
class TestMainViewModel {

    private lateinit var mainViewModel: MainViewModel
    private val mainRepository = Mockito.mock(MainRepository::class.java)

    @Before
    fun setup() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as TestMyApplication

        DaggerTestAppComponent
            .builder()
            .application(app)
            .build()
    }

    @Test
    fun testSearchFor() {
        Mockito.`when`(mainRepository.searchFor("query"))
            .thenReturn(Observable.just(listOf()))

        mainViewModel.imageListLiveData.observeForever {
            assert(it.isEmpty())
        }

        mainViewModel.searchFor("query")
    }

    @After
    fun teardown() {

    }
}