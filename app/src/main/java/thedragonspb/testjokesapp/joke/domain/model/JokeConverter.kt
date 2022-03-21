package thedragonspb.testjokesapp.joke.domain.model

import thedragonspb.testjokesapp.categories.domain.model.Category
import thedragonspb.testjokesapp.joke.gateway.response.JokeResponse

class JokeConverter {

    fun fromRemote(jokeResponse: JokeResponse): Joke {
        var category: Category? = null
        jokeResponse.categories.firstOrNull()?.let {
            category = Category(it)
        }
        return Joke(
            id = jokeResponse.id,
            text = jokeResponse.value,
            category = category
        )
    }

}