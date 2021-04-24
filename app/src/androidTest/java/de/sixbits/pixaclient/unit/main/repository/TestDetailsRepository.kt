package de.sixbits.pixaclient.unit.main.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TestDetailsRepository {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

}