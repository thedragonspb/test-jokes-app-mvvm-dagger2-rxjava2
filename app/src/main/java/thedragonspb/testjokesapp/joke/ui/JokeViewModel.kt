package thedragonspb.testjokesapp.joke.ui

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Single
import thedragonspb.testjokesapp.categories.domain.model.Category
import thedragonspb.testjokesapp.joke.domain.model.Joke
import thedragonspb.testjokesapp.joke.domain.JokeInteractor

class JokeViewModel(
    private val jokeInteractor: JokeInteractor
) : ViewModel() {

    var joke: Joke? = null

    var selectedCategory: Category? = null

    fun getRandomJoke(): Single<Joke> {
        return jokeInteractor.getRandomJoke(selectedCategory)
    }
}