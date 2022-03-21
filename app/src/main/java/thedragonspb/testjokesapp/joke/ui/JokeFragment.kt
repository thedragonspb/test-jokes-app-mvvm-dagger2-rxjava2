package thedragonspb.testjokesapp.joke.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import thedragonspb.testjokesapp.R
import thedragonspb.testjokesapp.app.ui.ViewModelFactory
import thedragonspb.testjokesapp.app.utils.getAppComponent
import thedragonspb.testjokesapp.categories.domain.model.Category
import thedragonspb.testjokesapp.databinding.FragmentJokeBinding
import thedragonspb.testjokesapp.joke.di.JokeComponent
import thedragonspb.testjokesapp.joke.domain.model.Joke
import timber.log.Timber

class JokeFragment : Fragment() {

    private lateinit var jokeComponent: JokeComponent

    private val viewModel: JokeViewModel by viewModels {
        ViewModelFactory { jokeComponent.jokeViewModel() }
    }

    private var _binding: FragmentJokeBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            jokeComponent = getAppComponent().jokeComponent().build()
            jokeComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJokeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()

        viewModel.selectedCategory = arguments?.get("category") as Category

        if (viewModel.joke != null) {
            initJokeView(viewModel.joke!!)
        } else {
            listenForJoke()
        }
    }

    private fun setupButtons() {
        binding.oneMoreJockButton.setOnClickListener {
            listenForJoke()
        }
    }

    private fun listenForJoke() {
        viewModel.getRandomJoke()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                hideOneMoreButton()
                showProgressBar()
            }
            .doFinally {
                hideProgressBar()
                showOneMoreButton()
            }
            .subscribe(object : DisposableSingleObserver<Joke>() {
                override fun onSuccess(joke: Joke) {
                    viewModel.joke = joke
                    initJokeView(joke)
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                    e.localizedMessage?.let { errorMessage ->
                        binding.jokeTextView.text =
                            resources.getString(R.string.loading_error, errorMessage)
                    }
                }
            })
    }

    private fun initJokeView(joke: Joke) {
        binding.jokeTextView.text =
            if (joke.category == null) {
                joke.text
            } else {
                resources.getString(R.string.joke_with_category, joke.text, joke.category.name)
            }
    }

    private fun showOneMoreButton() {
        binding.oneMoreJockButton.visibility = View.VISIBLE
    }

    private fun hideOneMoreButton() {
        binding.oneMoreJockButton.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}