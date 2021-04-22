package de.sixbits.pixaclient.unit.network.service

import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestPixabayService {

    lateinit var webServer: MockWebServer

    @BeforeAll
    fun setup() {
        webServer = MockWebServer()
        webServer.start()
    }

    @Test
    fun testGetSearchResult() {

    }

    @AfterAll
    fun cleanUp() {
        webServer.shutdown()
    }
}