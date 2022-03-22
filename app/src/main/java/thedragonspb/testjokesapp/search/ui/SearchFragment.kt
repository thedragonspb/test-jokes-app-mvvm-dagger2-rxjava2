package thedragonspb.testjokesapp.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import thedragonspb.testjokesapp.R
import thedragonspb.testjokesapp.app.ui.ViewModelFactory
import thedragonspb.testjokesapp.app.utils.getAppComponent
import thedragonspb.testjokesapp.databinding.SearchFragmentBinding
import thedragonspb.testjokesapp.joke.domain.model.Joke
import thedragonspb.testjokesapp.search.di.SearchComponent
import thedragonspb.testjokesapp.search.ui.adapter.SearchResultAdapter
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment() {

    private lateinit var searchComponent: SearchComponent

    private val viewModel: SearchViewModel by viewModels {
        ViewModelFactory { searchComponent.searchViewModel() }
    }

    private var _binding: SearchFragmentBinding? = null

    private val binding get() = _binding!!

    private var searchResultAdapter = SearchResultAdapter()

    private val searchViewObservable = Observable.create<String> { subscriber ->
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                subscriber.onNext(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                subscriber.onNext(newText)
                return false
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            searchComponent = getAppComponent().searchComponent().build()
            searchComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchResultView()
        observeSearchView()
    }

    private fun setupSearchResultView() {
        searchResultAdapter.stateRestorationPolicy = PREVENT_WHEN_EMPTY
        searchResultAdapter.searchResult = viewModel.searchResult

        binding.apply {
            searchResultRecyclerView.adapter = searchResultAdapter
            ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
                val dividerItemDecoration =
                    DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
                dividerItemDecoration.setDrawable(it)
                searchResultRecyclerView.addItemDecoration(dividerItemDecoration)
            }
        }
    }

    private fun observeSearchView() {
        searchViewObservable
            .debounce(300, TimeUnit.MILLISECONDS)
            .map { it.lowercase().trim() }
            .filter { it.isNotBlank() && it.length > 2 }
            .distinctUntilChanged()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.io())
            .switchMapSingle { searchText ->
                viewModel.search(searchText)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<List<Joke>>() {

                override fun onNext(searchResult: List<Joke>) {
                    searchResultAdapter.searchResult = searchResult
                }

                override fun onComplete() { }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                    showToast(resources.getString(R.string.loading_error, e.localizedMessage))
                }
            })
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }
}