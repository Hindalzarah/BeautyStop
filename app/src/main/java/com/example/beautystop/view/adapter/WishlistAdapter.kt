package com.example.beautystop.view.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.beautystop.databinding.WishlistItemLayoutBinding
import com.example.beautystop.models.WishlistModel
import com.example.beautystop.view.WishlistViewModel
import com.google.android.material.tabs.TabLayout
import com.squareup.picasso.Picasso

private const val TAG = "WishlistAdapter"
class WishlistAdapter(val viewModel: WishlistViewModel) :

    RecyclerView.Adapter<WishlistAdapter.FavoritesHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WishlistModel>() {
        override fun areItemsTheSame(oldItem: WishlistModel, newItem: WishlistModel): Boolean {
            return oldItem.id == newItem.id
        }


    override fun areContentsTheSame(oldItem: WishlistModel, newItem: WishlistModel): Boolean {
        return oldItem == newItem
    }
}


    private val differ = AsyncListDiffer(this,DIFF_CALLBACK)

    fun submitList(list: List<WishlistModel>) {
        differ.submitList(list)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavoritesHolder {


        val binding = WishlistItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoritesHolder(binding,viewModel)


    }

    override fun onBindViewHolder(holder: FavoritesHolder, position: Int) {
        val item = differ.currentList[position]

        holder.bind(item)

        //using the deleteFromWishlist function to delete items
        holder.delete.setOnClickListener {

            val wishlist = mutableListOf<WishlistModel>()
            //adding to the list
            wishlist.addAll(differ.currentList)
            //removing from the list
            wishlist.remove(item)

            //submitting it to the function
            viewModel.deleteFromWishlist(item.id)
            differ.submitList(wishlist)

        }

        var counter = item.quantity
        holder.plus.setOnClickListener{

            Log.d(TAG, counter.toString())
            counter++
            holder.amount.text = counter.toString()
            //update function to update the quantity in the list
            item.quantity = counter
            viewModel.editFromWishlist(item)
        }


        holder.minus.setOnClickListener{
            counter--
            holder.amount.text = counter.toString()

            //update function to update the quantity in the list
            item.quantity = counter
            viewModel.editFromWishlist(item)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class FavoritesHolder(val binding: WishlistItemLayoutBinding,val viewModel: WishlistViewModel) : RecyclerView.ViewHolder(binding.root) {

        val delete = binding.deleteButton
        val plus = binding.plusButton
        val minus = binding.minusButton
        val amount = binding.amountTextview
        fun bind(item: WishlistModel) {


            //setting values for the views using the model
            Picasso.get().load(item.image).into(binding.favoritesImageview)
            binding.productNameWishlistTv.text = item.name
            binding.amountTextview.text = item.quantity.toString()

//            var counter = 1
//            binding.plusButton.setOnClickListener {
//
//                counter++
//                binding.amountTextview.text = counter.toString()
//
//                //update function to update the quantity in the list
//                item.quantity = counter
//                viewModel.editFromWishlist(item)
//            }
//            binding.minusButton.setOnClickListener {
//                if(counter > 0) {
//                    counter--
//                    binding.amountTextview.text = counter.toString()
//
//                    //update function to update the quantity in the list
//                    item.quantity = counter
//                    viewModel.editFromWishlist(item)
//                }
            }


        }
    }
