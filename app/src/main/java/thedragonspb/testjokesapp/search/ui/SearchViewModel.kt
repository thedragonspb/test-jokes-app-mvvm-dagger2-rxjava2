package thedragonspb.testjokesapp.search.ui

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import thedragonspb.testjokesapp.joke.domain.model.Joke
import thedragonspb.testjokesapp.search.domain.SearchInteractor


class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

    val searchTextSubject: PublishSubject<String> = PublishSubject.create()

    var searchResult = mutableListOf<Joke>()

    fun search(searchText: String): Single<List<Joke>> {
        return searchInteractor.search(searchText)
            .map { newSearchResult ->
                searchResult.apply {
                    clear()
                    addAll(newSearchResult)
                }
                newSearchResult
            }
    }


}