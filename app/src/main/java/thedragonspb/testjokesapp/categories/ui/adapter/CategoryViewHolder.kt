package thedragonspb.testjokesapp.categories.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import thedragonspb.testjokesapp.categories.domain.model.Category
import thedragonspb.testjokesapp.databinding.ItemCategoryBinding
import java.util.*

class CategoryViewHolder(
    private val binding: ItemCategoryBinding,
    private val onCategorySelected: (category: Category) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(category: Category) {
        binding.categoryNameTextView.text = category.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }

        binding.root.setOnClickListener {
            onCategorySelected.invoke(category)
        }
    }
}