package thedragonspb.testjokesapp.categories.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thedragonspb.testjokesapp.categories.domain.model.Category
import thedragonspb.testjokesapp.databinding.ItemCategoryBinding

class CategoriesAdapter(
    private val onCategorySelected: (category: Category) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {

    var categories: List<Category> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, onCategorySelected)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

}