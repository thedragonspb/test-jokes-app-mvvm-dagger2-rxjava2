package thedragonspb.testjokesapp.search.gateway

import android.database.Observable
import thedragonspb.testjokesapp.search.gateway.response.SearchResponse

interface SearchGateway {

    fun search(searchText: String): io.reactivex.rxjava3.core.Observable<SearchResponse>
}