package thedragonspb.testjokesapp.categories.ui

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Single
import thedragonspb.testjokesapp.categories.domain.CategoriesInteractor
import thedragonspb.testjokesapp.categories.domain.model.Category

class CategoriesViewModel(
    private val categoriesInteractor: CategoriesInteractor
) : ViewModel() {


    fun categories(): Single<List<Category>> {
        return categoriesInteractor.categories()
    }

}