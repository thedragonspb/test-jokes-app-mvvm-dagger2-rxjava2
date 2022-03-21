package thedragonspb.testjokesapp.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thedragonspb.testjokesapp.databinding.ItemSearchResultBinding
import thedragonspb.testjokesapp.joke.domain.model.Joke

class SearchResultAdapter: RecyclerView.Adapter<SearchResultViewHolder>() {

    var searchResult = listOf<Joke>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val binding =
            ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(joke = searchResult[position])
    }

    override fun getItemCount(): Int {
        return searchResult.size
    }
}