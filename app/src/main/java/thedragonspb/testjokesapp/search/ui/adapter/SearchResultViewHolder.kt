package thedragonspb.testjokesapp.search.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import thedragonspb.testjokesapp.databinding.ItemSearchResultBinding
import thedragonspb.testjokesapp.joke.domain.model.Joke

class SearchResultViewHolder(
    binding: ItemSearchResultBinding
): RecyclerView.ViewHolder(binding.root) {

    private val jokeTextView = binding.jokeTextView

    fun bind(joke: Joke) {
        jokeTextView.text = joke.text
    }

}