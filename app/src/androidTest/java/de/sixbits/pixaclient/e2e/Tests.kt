package de.sixbits.pixaclient.e2e

import android.content.res.Resources
import android.view.KeyEvent
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.pressKey
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bumptech.glide.load.engine.DiskCacheStrategy.RESOURCE
import de.sixbits.pixaclient.R
import de.sixbits.pixaclient.main.ui.MainActivity
import de.sixbits.pixaclient.utils.ImmediateSchedulersRule
import de.sixbits.pixaclient.utils.typeTextSearchView
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val RESOURCE = "GLOBAL"

@RunWith(AndroidJUnit4::class)
class Tests {

    @get:Rule
    val mainScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val immediateSchedulersRule = ImmediateSchedulersRule()

    @JvmField
    val countingIdlingResource = CountingIdlingResource(de.sixbits.pixaclient.e2e.RESOURCE)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(countingIdlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource)
    }

    @Test
    fun testMainFlow() {
        onView(withId(R.id.et_search_bar))
            .perform(typeTextSearchView("Dogs"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))

        onView(withId(R.id.rv_search_result))
            .check(matches(isDisplayed()))

        // and i request a search query "dogs"
    }
}