package thedragonspb.testjokesapp.search.gateway

import io.reactivex.rxjava3.core.Single
import thedragonspb.testjokesapp.search.api.SearchApi
import thedragonspb.testjokesapp.search.gateway.response.SearchResponse

class SearchRemoteGateway(
    private val searchApi: SearchApi
): SearchGateway {

    override fun search(searchText: String): Single<SearchResponse> {
        return searchApi.search(searchText)
    }

}