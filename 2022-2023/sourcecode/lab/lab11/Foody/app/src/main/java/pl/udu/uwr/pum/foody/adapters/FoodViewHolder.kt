package pl.udu.uwr.pum.foody.adapters

import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pl.udu.uwr.pum.foody.data.Meal
import pl.udu.uwr.pum.foody.databinding.ListItemRvBinding
import pl.udu.uwr.pum.foody.ui.foodlist.FoodListFragmentDirections

class FoodViewHolder(private val binding: ListItemRvBinding)
    : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Meal){
            binding.name.text = item.strMeal
            Glide.with(binding.root)
                .load(item.strMealThumb)
                .into(binding.image)
            binding.root.setOnClickListener {
                val action: NavDirections = FoodListFragmentDirections
                    .actionFoodListFragmentToFoodDetailFragment(
                        item.idMeal
                    )
                Navigation.findNavController(binding.root).navigate(action)
            }
        }
}