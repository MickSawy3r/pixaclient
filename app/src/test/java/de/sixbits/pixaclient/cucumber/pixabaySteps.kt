package de.sixbits.pixaclient.cucumber

import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    plugin = [
        "pretty"
    ],
    features = ["src/test/assets/features"],
    publish = true
)
class pixabaySteps : En {
    init {
        Given("I have an access to Pixabay API") {
            assert(true)
        }
        Then("I get an images list result") {
            assert(true)
        }
        When("I request a search {string}") { num: String ->
            assert(true)
        }
    }
}