package thedragonspb.testjokesapp.categories.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import thedragonspb.testjokesapp.categories.gateway.response.CategoriesResponse

interface CategoriesApi {

    @GET("jokes/categories")
    fun categories(): Single<CategoriesResponse>

}