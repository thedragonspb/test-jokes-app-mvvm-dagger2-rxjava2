package thedragonspb.testjokesapp.joke.gateway

import io.reactivex.rxjava3.core.Single
import thedragonspb.testjokesapp.joke.domain.model.Joke
import thedragonspb.testjokesapp.joke.gateway.response.JokeResponse

interface JokeGateway {

    fun getRandomJoke(category: String?): Single<JokeResponse>

}