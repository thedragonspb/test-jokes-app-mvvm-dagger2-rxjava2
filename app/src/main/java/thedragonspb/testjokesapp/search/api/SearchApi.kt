package thedragonspb.testjokesapp.search.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import thedragonspb.testjokesapp.search.gateway.response.SearchResponse

interface SearchApi {

    @GET("/jokes/search")
    fun search(@Query("query") searchText: String): Single<SearchResponse>
}