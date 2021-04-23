package de.sixbits.pixaclient.main.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import de.sixbits.pixaclient.MyApplication
import de.sixbits.pixaclient.R
import de.sixbits.pixaclient.databinding.ActivityDetailsBinding
import de.sixbits.pixaclient.databinding.ActivityMainBinding
import de.sixbits.pixaclient.main.MainComponent
import de.sixbits.pixaclient.main.view_model.MainViewModel
import javax.inject.Inject

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainComponent: MainComponent
    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inject
        mainComponent = (application as MyApplication)
            .appComponent
            .mainComponent()
            .create()
        mainComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)

        mainViewModel.imageListLiveData.observe(this, {
            Log.d(TAG, "onCreate: ${it.size}")
        })

        mainViewModel.getCachedImages()
    }
}