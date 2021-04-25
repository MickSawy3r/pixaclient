package de.sixbits.pixaclient.unit.main.view_model

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import de.sixbits.pixaclient.ImageResponseFactory
import de.sixbits.pixaclient.main.repository.MainRepository
import de.sixbits.pixaclient.main.view_model.MainViewModel
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4ClassRunner::class)
class TestMainViewModel {

    private lateinit var mainViewModel: MainViewModel
    private val mainRepository = Mockito.mock(MainRepository::class.java)

    @Test
    fun testSearchFor() {
        Mockito.`when`(mainRepository.searchFor("query"))
            .thenReturn(Observable.just(listOf(ImageResponseFactory.getImageListItem())))

        mainViewModel = MainViewModel(mainRepository)

        mainViewModel.imageListLiveData.observeForever {
            assert(it.isEmpty())
        }

        mainViewModel.searchFor("query")
    }
}