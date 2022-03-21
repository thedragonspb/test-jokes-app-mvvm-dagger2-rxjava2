package thedragonspb.testjokesapp.categories.domain.model

import thedragonspb.testjokesapp.categories.gateway.response.CategoriesResponse

class CategoriesConverter {

    fun fromRemote(categoriesResponse: CategoriesResponse): List<Category> {
        val categories = mutableListOf<Category>()
        categoriesResponse.map {
            categories.add(Category(it))
        }
        return categories
    }

}