package de.sixbits.pixaclient.main.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.sixbits.pixaclient.MyApplication
import de.sixbits.pixaclient.databinding.ActivityMainBinding
import de.sixbits.pixaclient.main.MainComponent
import de.sixbits.pixaclient.main.adapters.SearchResultRecyclerAdapter
import de.sixbits.pixaclient.main.callbacks.OnImageClickListener
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
    private lateinit var searchRecyclerRequestBuilder: RequestBuilder<Drawable>

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

        initViews()
        initViewModel()
        initRecyclerView()
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)
        mainViewModel.imageListLiveData.observe(this, {
            searchRecyclerAdapter =
                SearchResultRecyclerAdapter(it, requestBuilder = searchRecyclerRequestBuilder, this)
            binding.rvSearchResult.adapter = searchRecyclerAdapter
        })
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
            binding.rvSearchResult.layoutManager = GridLayoutManager(this,  2)
        } else {
            binding.rvSearchResult.layoutManager = GridLayoutManager(this,  1)
        }
        binding.tilSearchBar.setEndIconOnClickListener {
            mainViewModel.searchFor(binding.etSearchBar.text.toString())
        }
        binding.etSearchBar.setText("Fruits")
    }

    private fun initRecyclerView() {
        // For Preloading images
        searchRecyclerRequestBuilder = Glide.with(this)
            .asDrawable()
        val preloadSizeProvider =
            ViewPreloadSizeProvider<ImageListItemModel>()

        // initially we have no items
        searchRecyclerAdapter =
            SearchResultRecyclerAdapter(
                listOf(),
                searchRecyclerRequestBuilder,
                this
            )

        val preLoader = RecyclerViewPreloader(
            Glide.with(this),
            searchRecyclerAdapter,
            preloadSizeProvider,
            4
        )

        // attaching the preloader
        binding.rvSearchResult.addOnScrollListener(preLoader)
    }

    override fun onClick(id: Int) {
        MaterialAlertDialogBuilder(this)
            .setMessage("Are you sure you want to view this entry?")
            .setPositiveButton("Yes") { _, _ ->
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
            .setNegativeButton("No") { _, _ ->
                // Respond to positive button press
            }
            .show()
    }
}