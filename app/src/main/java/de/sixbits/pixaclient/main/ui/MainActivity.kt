package de.sixbits.pixaclient.main.ui

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.util.ViewPreloadSizeProvider
import de.sixbits.pixaclient.MyApplication
import de.sixbits.pixaclient.databinding.ActivityMainBinding
import de.sixbits.pixaclient.main.MainComponent
import de.sixbits.pixaclient.main.adapters.SearchResultRecyclerAdapter
import de.sixbits.pixaclient.main.view_model.MainViewModel
import de.sixbits.pixaclient.network.model.ImageListItemModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
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

        mainViewModel.getCachedImages()
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)

        mainViewModel.imageListLiveData.observe(this, {
            searchRecyclerAdapter =
                SearchResultRecyclerAdapter(it, requestBuilder = searchRecyclerRequestBuilder)
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

        mainViewModel.searchFor("Fruits")
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        binding.rvSearchResult.layoutManager = LinearLayoutManager(this)
        binding.tilSearchBar.setEndIconOnClickListener {
            mainViewModel.searchFor(binding.etSearchBar.text.toString())
        }
        binding.etSearchBar.setText("Fruits")

        searchRecyclerRequestBuilder = Glide.with(this)
            .asDrawable()
        val preloadSizeProvider =
            ViewPreloadSizeProvider<ImageListItemModel>()

        searchRecyclerAdapter = SearchResultRecyclerAdapter(listOf(), searchRecyclerRequestBuilder)

        val preLoader =
            RecyclerViewPreloader(
                Glide.with(this),
                searchRecyclerAdapter,
                preloadSizeProvider,
                4
            )

        binding.rvSearchResult.addOnScrollListener(preLoader)
    }
}