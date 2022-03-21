package thedragonspb.testjokesapp.joke.domain

import io.reactivex.rxjava3.core.Single
import thedragonspb.testjokesapp.categories.domain.model.Category
import thedragonspb.testjokesapp.joke.domain.model.Joke
import thedragonspb.testjokesapp.joke.domain.model.JokeConverter
import thedragonspb.testjokesapp.joke.gateway.JokeGateway

class JokeInteractor(
    private val jokeGateway: JokeGateway,
    private val jokeConverter: JokeConverter
) {

    fun getRandomJoke(category: Category?): Single<Joke> {
        return jokeGateway.getRandomJoke(category?.name)
            .map { jokeConverter.fromRemote(it) }
    }
}