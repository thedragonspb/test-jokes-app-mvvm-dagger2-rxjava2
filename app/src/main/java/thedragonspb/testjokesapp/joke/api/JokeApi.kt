package thedragonspb.testjokesapp.joke.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import thedragonspb.testjokesapp.joke.gateway.response.JokeResponse

interface JokeApi {

    @GET("jokes/random")
    fun randomJoke(): Single<JokeResponse>

    @GET("jokes/random")
    fun randomJokeByCategory(@Query("category") category: String): Single<JokeResponse>

}