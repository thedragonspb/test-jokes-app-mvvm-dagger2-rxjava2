package thedragonspb.testjokesapp.search.domain

import io.reactivex.rxjava3.core.Observable
import thedragonspb.testjokesapp.joke.domain.model.Joke
import thedragonspb.testjokesapp.search.domain.model.SearchResultConverter
import thedragonspb.testjokesapp.search.gateway.SearchGateway
import timber.log.Timber

class SearchInteractor(
    private val searchGateway: SearchGateway,
    private val searchResultConverter: SearchResultConverter
) {

    fun search(searchText: String): Observable<List<Joke>> {
        return searchGateway.search(searchText)
            .map {
                searchResultConverter.fromRemote(it)
            }
    }

}