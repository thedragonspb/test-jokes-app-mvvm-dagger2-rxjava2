package thedragonspb.testjokesapp.app.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import thedragonspb.testjokesapp.categories.di.CategoriesComponent
import thedragonspb.testjokesapp.joke.di.JokeComponent
import thedragonspb.testjokesapp.search.di.SearchComponent
import javax.inject.Scope

@Scope
annotation class AppScope

@Component(modules = [AppModule::class])
@AppScope
interface AppComponent {

    fun categoriesComponent(): CategoriesComponent.Builder

    fun jokeComponent(): JokeComponent.Builder

    fun searchComponent(): SearchComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}

@Module(subcomponents = [JokeComponent::class, CategoriesComponent::class, SearchComponent::class])
object AppModule {

    private const val BASE_URL = "https://api.chucknorris.io/"

    @Provides
    @AppScope
    fun provideJokesApi(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}