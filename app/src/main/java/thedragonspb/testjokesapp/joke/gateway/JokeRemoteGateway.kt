package thedragonspb.testjokesapp.joke.gateway

import io.reactivex.rxjava3.core.Single
import thedragonspb.testjokesapp.joke.api.JokeApi
import thedragonspb.testjokesapp.joke.domain.model.Joke
import thedragonspb.testjokesapp.joke.domain.model.JokeConverter
import thedragonspb.testjokesapp.joke.gateway.response.JokeResponse

class JokeRemoteGateway(
    private val jokeApi: JokeApi
) : JokeGateway {

    override fun getRandomJoke(category: String?): Single<JokeResponse> {
        return if (category.isNullOrEmpty()) {
            jokeApi.randomJoke()
        } else {
            jokeApi.randomJokeByCategory(category)
        }
    }

}