package de.sixbits.pixaclient.main.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import de.sixbits.pixaclient.MyApplication
import de.sixbits.pixaclient.R
import de.sixbits.pixaclient.databinding.ActivityDetailsBinding
import de.sixbits.pixaclient.main.MainComponent
import de.sixbits.pixaclient.main.keys.IntentKeys
import de.sixbits.pixaclient.main.view_model.DetailsViewModel
import javax.inject.Inject

private const val TAG = "DetailsActivity"

class DetailsActivity : AppCompatActivity() {
    // Injected members
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mainComponent: MainComponent

    lateinit var detailsViewModel: DetailsViewModel
    private lateinit var binding: ActivityDetailsBinding

    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private var currentAnimator: Animator? = null

    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private var shortAnimationDuration: Int = 0

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
            binding.tvDetailsUsername.text = it.username
            Glide.with(this)
                .load(it.image)
                .into(binding.ivDetailsImage)
        })
    }
}
