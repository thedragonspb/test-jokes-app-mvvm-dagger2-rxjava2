package thedragonspb.testjokesapp.search.domain.model

import thedragonspb.testjokesapp.categories.domain.model.Category
import thedragonspb.testjokesapp.joke.domain.model.Joke
import thedragonspb.testjokesapp.search.gateway.response.SearchResponse

class SearchResultConverter {

    fun fromRemote(response: SearchResponse): List<Joke> {
        val jokeArray = ArrayList<Joke>(response.total)
        response.searchResult.forEach { searchResult ->
            var category: Category? = null
            searchResult.categories.firstOrNull()?.let {
                category = Category(it)
            }
            val joke = Joke(
                id = searchResult.id,
                text = searchResult.value,
                category = category
            )
            jokeArray.add(joke)
        }
        return jokeArray
    }
}