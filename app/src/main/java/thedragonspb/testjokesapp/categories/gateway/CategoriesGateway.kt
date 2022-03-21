package thedragonspb.testjokesapp.categories.gateway

import io.reactivex.rxjava3.core.Single
import thedragonspb.testjokesapp.categories.domain.model.Category
import thedragonspb.testjokesapp.categories.gateway.response.CategoriesResponse

interface CategoriesGateway {

    fun categories(): Single<CategoriesResponse>

}