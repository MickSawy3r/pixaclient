package de.sixbits.pixaclient.main.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import de.sixbits.pixaclient.databinding.RowImagesListBinding
import de.sixbits.pixaclient.network.model.ImageListItemModel

class SearchResultRecyclerAdapter constructor(
    private val searchResult: List<ImageListItemModel>,
    private val requestBuilder: RequestBuilder<Drawable>
) :
    RecyclerView.Adapter<SearchResultRecyclerAdapter.SearchResultRecyclerViewHolder>(),
    ListPreloader.PreloadModelProvider<ImageListItemModel> {

    class SearchResultRecyclerViewHolder(val binding: RowImagesListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultRecyclerViewHolder {
        return SearchResultRecyclerViewHolder(
            RowImagesListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchResultRecyclerViewHolder, position: Int) {
        holder.binding.tvImageItemUsername.text = searchResult[position].username
        holder.binding.tvImageItemTags.text = searchResult[position].tags
        requestBuilder.load(searchResult[position].thumbnail)
            .into(holder.binding.ivImageItemThumbnail)
    }

    override fun getItemCount(): Int = searchResult.size

    override fun getPreloadItems(position: Int): MutableList<ImageListItemModel> {
        return if (searchResult.isNotEmpty())
            mutableListOf(searchResult[position])
        else
            mutableListOf()
    }

    override fun getPreloadRequestBuilder(item: ImageListItemModel): RequestBuilder<*> {
        return requestBuilder.load(item)
    }
}