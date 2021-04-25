package de.sixbits.pixaclient

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import de.sixbits.pixaclient.cucumber.TestMyApplication

class MyUnitTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, TestMyApplication::class.java.name, context)
    }

}