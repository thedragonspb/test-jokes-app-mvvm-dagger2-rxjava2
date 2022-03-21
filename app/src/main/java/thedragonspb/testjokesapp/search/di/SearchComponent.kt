package thedragonspb.testjokesapp.search.di

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import retrofit2.Retrofit
import thedragonspb.testjokesapp.search.api.SearchApi
import thedragonspb.testjokesapp.search.domain.SearchInteractor
import thedragonspb.testjokesapp.search.domain.model.SearchResultConverter
import thedragonspb.testjokesapp.search.gateway.SearchGateway
import thedragonspb.testjokesapp.search.gateway.SearchRemoteGateway
import thedragonspb.testjokesapp.search.ui.SearchFragment
import thedragonspb.testjokesapp.search.ui.SearchViewModel
import javax.inject.Scope

@Scope
annotation class SearchScope

@SearchScope
@Subcomponent(modules = [SearchModule::class])
interface SearchComponent {

    fun inject(searchFragment: SearchFragment)

    fun searchViewModel(): SearchViewModel

    @Subcomponent.Builder
    interface Builder {
        fun build(): SearchComponent
    }
}

@Module
object SearchModule {

    @SearchScope
    @Provides
    fun provideSearchViewModel(searchInteractor: SearchInteractor) =
        SearchViewModel(searchInteractor)

    @SearchScope
    @Provides
    fun provideSearchInteractor(searchGateway: SearchGateway): SearchInteractor {
        return SearchInteractor(searchGateway, SearchResultConverter())
    }

    @SearchScope
    @Provides
    fun provideSearchGateway(searchApi: SearchApi): SearchGateway {
        return SearchRemoteGateway(searchApi)
    }

    @SearchScope
    @Provides
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.create(SearchApi::class.java)
    }

}