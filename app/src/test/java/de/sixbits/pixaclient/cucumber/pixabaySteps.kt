package de.sixbits.pixaclient.cucumber

import de.sixbits.pixaclient.config.Consts
import de.sixbits.pixaclient.network.manager.PixabayManager
import de.sixbits.pixaclient.network.model.ImageListItemModel
import de.sixbits.pixaclient.network.service.PixabayService
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(Cucumber::class)
@CucumberOptions(
    plugin = [
        "pretty"
    ],
    features = ["src/test/assets/features"],
//    publish = true
)
class pixabaySteps : En {
    init {
        Given("I have an access to Pixabay API") {
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
        When("I request a search {string}") { query: String ->
            searchObservable = pixabayManager.getSearchResult(query)
        }
        Then("I get an images list result") {
            searchObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    assert(it.isNotEmpty())
                }
        }
        When("I request an image with a valid id {int}") { imageId: Int ->

        }
        Then("I should get an Image from user {string}") { authorName: String ->

        }
    }

    companion object {
        lateinit var pixabayManager: PixabayManager
        lateinit var searchObservable: Observable<List<ImageListItemModel>>
    }
}