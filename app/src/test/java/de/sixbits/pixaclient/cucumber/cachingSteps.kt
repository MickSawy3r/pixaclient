/**
 * End-To-End Integration JVM test, This test is designed to test platform agnostic logic,
 * And it's integration across all services
 *
 * Caching Network Calls Feature
 *
 * @author Mohammad Al Kalaleeb
 */

package de.sixbits.pixaclient.cucumber

import de.sixbits.pixaclient.config.Consts
import de.sixbits.pixaclient.network.manager.PixabayManager
import de.sixbits.pixaclient.network.model.ImageDetailsModel
import de.sixbits.pixaclient.network.model.ImageListItemModel
import de.sixbits.pixaclient.network.service.PixabayService
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(Cucumber::class)
@CucumberOptions(
    plugin = [
        "pretty"
    ],
    features = ["src/test/assets/features/caching.feature"],
    publish = true
)
class cachingSteps : En {
    init {
        Given("I have an access to Pixabay Details API") {
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

            val retrofit = Retrofit
                .Builder()
                .baseUrl(Consts.API_ROOT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            val pixabayService = retrofit.create(PixabayService::class.java)
            pixabayManager = PixabayManager(pixabayService)
        }
        When("I disconnect from the internet") {

        }
        And("I have accessed the details of the image with id {int}") { imageId: Int ->
            searchObservable = pixabayManager.getImageDetails(imageId)
        }
        And("I request the cached images") {

        }
        Then("I should get a cached result that contains the image with id {int}") { imageId: Int ->

        }

    }

    companion object {
        lateinit var pixabayManager: PixabayManager
        lateinit var searchObservable: Observable<ImageDetailsModel>
    }
}