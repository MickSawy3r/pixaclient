package de.sixbits.pixaclient.e2e

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import de.sixbits.pixaclient.main.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Tests {
    @get:Rule
    val mainScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testMainFlow() {
        // when the app is running
        launchActivity<MainActivity>()

        // and i request a search query "dogs"

    }
}