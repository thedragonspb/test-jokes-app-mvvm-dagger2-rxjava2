package thedragonspb.testjokesapp.search.domain

import io.reactivex.rxjava3.core.Single
import thedragonspb.testjokesapp.joke.domain.model.Joke
import thedragonspb.testjokesapp.search.domain.model.SearchResultConverter
import thedragonspb.testjokesapp.search.gateway.SearchGateway

class SearchInteractor(
    private val searchGateway: SearchGateway,
    private val searchResultConverter: SearchResultConverter
) {

    fun search(searchText: String): Single<List<Joke>> {
        return searchGateway.search(searchText)
            .map {
                searchResultConverter.fromRemote(it)
            }
    }

}