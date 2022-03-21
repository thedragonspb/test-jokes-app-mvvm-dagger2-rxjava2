package thedragonspb.testjokesapp.categories.di

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import retrofit2.Retrofit
import thedragonspb.testjokesapp.categories.api.CategoriesApi
import thedragonspb.testjokesapp.categories.domain.CategoriesInteractor
import thedragonspb.testjokesapp.categories.domain.model.CategoriesConverter
import thedragonspb.testjokesapp.categories.gateway.CategoriesGateway
import thedragonspb.testjokesapp.categories.gateway.CategoriesRemoteGateway
import thedragonspb.testjokesapp.categories.ui.CategoriesFragment
import thedragonspb.testjokesapp.categories.ui.CategoriesViewModel
import javax.inject.Scope

@Scope
annotation class CategoriesScope

@CategoriesScope
@Subcomponent(modules = [CategoriesModule::class])
interface CategoriesComponent {

    fun inject(categoriesFragment: CategoriesFragment)

    fun categoriesViewModel(): CategoriesViewModel

    @Subcomponent.Builder
    interface Builder {
        fun build(): CategoriesComponent
    }
}

@Module
object CategoriesModule {

    @CategoriesScope
    @Provides
    fun provideCategoriesViewModel(categoriesInteractor: CategoriesInteractor) =
        CategoriesViewModel(categoriesInteractor)

    @CategoriesScope
    @Provides
    fun provideCategoriesInteractor(categoriesGateway: CategoriesGateway) =
        CategoriesInteractor(categoriesGateway, CategoriesConverter())

    @CategoriesScope
    @Provides
    fun provideCategoriesGateway(categoriesApi: CategoriesApi): CategoriesGateway =
        CategoriesRemoteGateway(categoriesApi)

    @CategoriesScope
    @Provides
    fun provideCategoriesApi(retrofit: Retrofit): CategoriesApi {
        return retrofit.create(CategoriesApi::class.java)
    }
}