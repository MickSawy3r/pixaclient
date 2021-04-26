package de.sixbits.pixaclient.main.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import de.sixbits.pixaclient.MyApplication
import de.sixbits.pixaclient.databinding.ActivityDetailsBinding
import de.sixbits.pixaclient.main.MainComponent
import de.sixbits.pixaclient.main.keys.IntentKeys
import de.sixbits.pixaclient.main.view_model.DetailsViewModel
import javax.inject.Inject


class DetailsActivity : AppCompatActivity() {
    // Injected members
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainComponent: MainComponent

    private lateinit var detailsViewModel: DetailsViewModel
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

        initViews()
        initViewModels()

        detailsViewModel.getImageDetails(intent.getIntExtra(IntentKeys.DETAILS_ID_KEY, -1))
    }

    private fun initViews() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initViewModels() {
        detailsViewModel = ViewModelProvider(this, viewModelFactory)
            .get(DetailsViewModel::class.java)

        detailsViewModel.detailsLiveData.observe(this, {
            binding.tvDetailsComments.text = "${it.comments}"
            binding.tvDetailsLikes.text = "${it.likes}"
            binding.tvDetailsLove.text = "${it.favorites}"
            binding.tvDetailsTags.text = it.tags
            binding.tvDetailsUsername.text = it.username
            Glide.with(this)
                .load(it.image)
                .into(binding.ivDetailsImage)
        })
    }
}
