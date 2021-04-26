package de.sixbits.pixaclient.main.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.sixbits.pixaclient.MyApplication
import de.sixbits.pixaclient.databinding.ActivityMainBinding
import de.sixbits.pixaclient.main.MainComponent
import de.sixbits.pixaclient.main.adapters.SearchResultRecyclerAdapter
import de.sixbits.pixaclient.main.callbacks.OnImageClickListener
import de.sixbits.pixaclient.main.keys.IntentKeys
import de.sixbits.pixaclient.main.view_model.MainViewModel
import de.sixbits.pixaclient.network.model.ImageListItemModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnImageClickListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mainComponent: MainComponent
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    private lateinit var searchRecyclerAdapter: SearchResultRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (application as MyApplication)
            .appComponent
            .mainComponent()
            .create()
        mainComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initRecyclerView()
        initViewModel()
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            viewModelFactory
        ).get(MainViewModel::class.java)

        // Handle data response
        mainViewModel.searchImagesLiveData.observe(this, {
            searchRecyclerAdapter.switchItems(it)
        })

        // Handle Loading Events
        mainViewModel.loadingLiveData.observe(this, {
            if (it) {
                binding.pbLoadingSearchResult.visibility = VISIBLE
                binding.rvSearchResult.visibility = INVISIBLE
            } else {
                binding.pbLoadingSearchResult.visibility = GONE
                binding.rvSearchResult.visibility = VISIBLE
            }
        })

        // initiating the first view
        mainViewModel.searchFor("Fruits")
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        if (resources.configuration.orientation == OrientationHelper.HORIZONTAL) {
            binding.rvSearchResult.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvSearchResult.layoutManager = GridLayoutManager(this, 1)
        }

        binding.etSearchBar.setOnSearchClickListener {
            // Request the search
            mainViewModel.searchFor(binding.etSearchBar.query.toString())
        }
        binding.etSearchBar.setQuery("Fruits", false)
    }

    private fun initRecyclerView() {
        // For Preloading images
        val searchRecyclerRequestBuilder = Glide.with(this).asDrawable()
        val preloadSizeProvider = ViewPreloadSizeProvider<ImageListItemModel>()

        // initially we have no items
        searchRecyclerAdapter = SearchResultRecyclerAdapter(
            listOf(),
            searchRecyclerRequestBuilder,
            this
        )

        // For Preloading  images
        val preLoader = RecyclerViewPreloader(
            Glide.with(this),
            searchRecyclerAdapter,
            preloadSizeProvider,
            4
        )

        // Attach the adapter
        binding.rvSearchResult.adapter = searchRecyclerAdapter
        searchRecyclerAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        // attaching the preloader
        binding.rvSearchResult.addOnScrollListener(preLoader)
    }

    override fun onClick(id: Int) {
        MaterialAlertDialogBuilder(this)
            .setMessage("Are you sure you want to view this entry?")
            .setPositiveButton("Yes") { _, _ ->
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra(IntentKeys.DETAILS_ID_KEY, id)
                startActivity(intent)
            }
            .setNegativeButton("No") { _, _ ->
                // Respond to positive button press
            }
            .show()
    }
}