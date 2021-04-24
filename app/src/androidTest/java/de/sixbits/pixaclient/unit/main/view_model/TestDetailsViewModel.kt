package de.sixbits.pixaclient.unit.main.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import de.sixbits.pixaclient.main.repository.DetailsRepository
import de.sixbits.pixaclient.main.repository.MainRepository
import de.sixbits.pixaclient.main.view_model.DetailsViewModel
import de.sixbits.pixaclient.main.view_model.MainViewModel
import de.sixbits.pixaclient.util.ImageResponseFactory
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4ClassRunner::class)
class TestDetailsViewModel {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var detailsViewModel: DetailsViewModel
    private val detailsRepository = Mockito.mock(DetailsRepository::class.java)

    @Test
    fun testGetImageDetails() {
        val expectedResponse = ImageResponseFactory.getImageDetails()

        Mockito.`when`(detailsRepository.getImageDetails(123))
            .thenReturn(Observable.just(expectedResponse))

        detailsViewModel = DetailsViewModel(detailsRepository)

        detailsViewModel.detailsLiveData.observeForever {
            assert(it.id == expectedResponse.id)
        }

        detailsViewModel.getImageDetails(123)
    }
}