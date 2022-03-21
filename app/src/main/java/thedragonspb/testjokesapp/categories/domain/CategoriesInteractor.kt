package thedragonspb.testjokesapp.categories.domain

import io.reactivex.rxjava3.core.Single
import thedragonspb.testjokesapp.categories.domain.model.CategoriesConverter
import thedragonspb.testjokesapp.categories.domain.model.Category
import thedragonspb.testjokesapp.categories.gateway.CategoriesGateway

class CategoriesInteractor(
    private val categoriesGateway: CategoriesGateway,
    private val categoriesConverter: CategoriesConverter
) {

    fun categories(): Single<List<Category>> {
        return categoriesGateway.categories().map {
            categoriesConverter.fromRemote(it)
        }
    }

}