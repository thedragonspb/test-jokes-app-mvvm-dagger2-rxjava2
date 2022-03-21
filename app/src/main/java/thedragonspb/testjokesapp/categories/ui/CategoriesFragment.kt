package thedragonspb.testjokesapp.categories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import thedragonspb.testjokesapp.R
import thedragonspb.testjokesapp.app.ui.ViewModelFactory
import thedragonspb.testjokesapp.app.utils.getAppComponent
import thedragonspb.testjokesapp.categories.di.CategoriesComponent
import thedragonspb.testjokesapp.categories.domain.model.Category
import thedragonspb.testjokesapp.categories.ui.adapter.CategoriesAdapter
import thedragonspb.testjokesapp.databinding.FragmentCategoriesBinding
import timber.log.Timber

class CategoriesFragment : Fragment() {

    lateinit var categoriesComponent: CategoriesComponent

    private val viewModel: CategoriesViewModel by viewModels {
        ViewModelFactory { categoriesComponent.categoriesViewModel() }
    }

    private var _binding: FragmentCategoriesBinding? = null

    private val binding get() = _binding!!

    private var categoriesAdapter: CategoriesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            categoriesComponent = getAppComponent().categoriesComponent().build()
            categoriesComponent.inject(this)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoriesRecyclerView()
        setupButtons()
        listenForCategories()
    }

    private fun setupButtons() {
        binding.apply {
            repeatButton.setOnClickListener {
                listenForCategories()
            }

            searchButton.setOnClickListener {
                findNavController().navigate(
                    CategoriesFragmentDirections.toSearchFragment()
                )
            }
        }
    }

    private fun setupCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter { selectedCategory ->
            findNavController().navigate(
                CategoriesFragmentDirections.toJokeFragment(selectedCategory)
            )
        }
        categoriesAdapter?.stateRestorationPolicy =
            StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.apply {
            categoriesRecyclerView.adapter = categoriesAdapter

            ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
                val dividerItemDecoration =
                    DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
                dividerItemDecoration.setDrawable(it)
                categoriesRecyclerView.addItemDecoration(dividerItemDecoration)
            }
        }
    }

    private fun listenForCategories() {
        viewModel.categories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                hideRecyclerView()
                hideErrorView()
                showProgressBar()
            }
            .doFinally {
                hideProgressBar()
            }
            .subscribe(object : DisposableSingleObserver<List<Category>>() {
                override fun onSuccess(categories: List<Category>) {
                    categoriesAdapter?.categories = categories
                    showRecyclerView()
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                    showErrorView(e.localizedMessage)
                }
            })
    }

    private fun showRecyclerView() {
        binding.categoriesRecyclerView.visibility = View.VISIBLE
    }

    private fun hideRecyclerView() {
        binding.categoriesRecyclerView.visibility = View.GONE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showErrorView(localizedMessage: String?) {
        binding.apply {
            repeatButton.visibility = View.VISIBLE
            errorTextView.visibility = View.VISIBLE
            localizedMessage?.let {
                errorTextView.text = resources.getString(R.string.loading_error, localizedMessage)
            }
        }
    }

    private fun hideErrorView() {
        binding.apply {
            errorTextView.visibility = View.GONE
            repeatButton.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}