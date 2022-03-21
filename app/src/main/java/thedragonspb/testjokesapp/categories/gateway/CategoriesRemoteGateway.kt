package thedragonspb.testjokesapp.categories.gateway

import io.reactivex.rxjava3.core.Single
import thedragonspb.testjokesapp.categories.api.CategoriesApi
import thedragonspb.testjokesapp.categories.domain.model.CategoriesConverter
import thedragonspb.testjokesapp.categories.domain.model.Category
import thedragonspb.testjokesapp.categories.gateway.response.CategoriesResponse

class CategoriesRemoteGateway(
    private val categoriesApi: CategoriesApi
) : CategoriesGateway {

    override fun categories(): Single<CategoriesResponse> {
        return categoriesApi.categories()
    }
}