package de.sixbits.pixaclient.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import de.sixbits.pixaclient.MyApplication
import de.sixbits.pixaclient.R
import de.sixbits.pixaclient.main.MainComponent
import de.sixbits.pixaclient.main.view_model.DetailsViewModel
import de.sixbits.pixaclient.main.view_model.MainViewModel
import javax.inject.Inject

private const val TAG = "DetailsActivity"

class DetailsActivity : AppCompatActivity() {
    // Injected members
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mainComponent: MainComponent

    lateinit var detailsViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inject
        mainComponent = (application as MyApplication)
            .appComponent
            .mainComponent()
            .create()
        mainComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)

        detailsViewModel.detailsLiveData.observe(this, {
            Log.d(TAG, "onCreate: ${it.username}")
        })
    }
}
