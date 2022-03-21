package thedragonspb.testjokesapp.joke.di

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import retrofit2.Retrofit
import thedragonspb.testjokesapp.joke.api.JokeApi
import thedragonspb.testjokesapp.joke.domain.model.JokeConverter
import thedragonspb.testjokesapp.joke.domain.JokeInteractor
import thedragonspb.testjokesapp.joke.gateway.JokeGateway
import thedragonspb.testjokesapp.joke.gateway.JokeRemoteGateway
import thedragonspb.testjokesapp.joke.ui.JokeFragment
import thedragonspb.testjokesapp.joke.ui.JokeViewModel
import javax.inject.Scope

@Scope
annotation class JokeScope

@JokeScope
@Subcomponent(modules = [JokeModule::class])
interface JokeComponent {

    fun inject(jokeFragment: JokeFragment)

    fun jokeViewModel(): JokeViewModel

    @Subcomponent.Builder
    interface Builder {
        fun build(): JokeComponent
    }
}

@Module
object JokeModule {

    @JokeScope
    @Provides
    fun provideJokeViewModel(jokeInteractor: JokeInteractor) =
        JokeViewModel(jokeInteractor)

    @Provides
    fun provideJokeInteractor(jokeGateway: JokeGateway): JokeInteractor {
        return JokeInteractor(jokeGateway, JokeConverter())
    }

    @JokeScope
    @Provides
    fun provideJokeGateway(jokeApi: JokeApi): JokeGateway {
        return JokeRemoteGateway(jokeApi)
    }

    @JokeScope
    @Provides
    fun provideJokeApi(retrofit: Retrofit): JokeApi {
        return retrofit.create(JokeApi::class.java)
    }
}