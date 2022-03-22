package thedragonspb.testjokesapp.search.gateway

import io.reactivex.rxjava3.core.Single
import thedragonspb.testjokesapp.search.gateway.response.SearchResponse

interface SearchGateway {

    fun search(searchText: String): Single<SearchResponse>
}