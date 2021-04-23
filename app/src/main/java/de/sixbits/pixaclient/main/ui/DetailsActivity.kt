package de.sixbits.pixaclient.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import de.sixbits.pixaclient.MyApplication
import de.sixbits.pixaclient.R
import de.sixbits.pixaclient.databinding.ActivityDetailsBinding
import de.sixbits.pixaclient.main.MainComponent
import de.sixbits.pixaclient.main.keys.IntentKeys
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

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inject
        mainComponent = (application as MyApplication)
            .appComponent
            .mainComponent()
            .create()
        mainComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailsViewModel = ViewModelProvider(this, viewModelFactory)
            .get(DetailsViewModel::class.java)

        detailsViewModel.detailsLiveData.observe(this, {
            binding.tvDetailsComments.text = "${it.comments}"
            binding.tvDetailsLikes.text = "${it.likes}"
            binding.tvDetailsLove.text = "${it.favorites}"
            binding.tvDetailsUsername.text = it.username
            Glide.with(this)
                .load(it.image)
                .into(binding.ivDetailsImage)
        })


        detailsViewModel.getImageDetails(intent.getIntExtra(IntentKeys.DETAILS_ID_KEY, -1))
    }
}
