package com.example.beautystop.view.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.beautystop.databinding.WishlistItemLayoutBinding
import com.example.beautystop.models.WishlistModel
import com.example.beautystop.view.WishlistViewModel
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


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
    ): WishlistAdapter.FavoritesHolder {


        val binding = WishlistItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoritesHolder(binding,viewModel)


    }

    override fun onBindViewHolder(holder: FavoritesHolder, position: Int) {
        val item = differ.currentList[position]

        holder.bind(item)

        holder.delete.setOnClickListener {

            val wishlist = mutableListOf<WishlistModel>()
            wishlist.addAll(differ.currentList)
            wishlist.remove(item)

            viewModel.deleteFromWishlist(item.id)

            differ.submitList(wishlist)

        }


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class FavoritesHolder(val binding: WishlistItemLayoutBinding,val viewModel: WishlistViewModel) : RecyclerView.ViewHolder(binding.root) {

        val delete = binding.deleteButton
        fun bind(item: WishlistModel) {

            var counter = 1
            binding.plusButton.setOnClickListener {

                counter++
                binding.amountTextview.text = counter.toString()

                item.quantity = counter

                viewModel.editFromWishlist(item)
            }
            binding.minusButton.setOnClickListener {
                if(counter > 0) {
                    counter--
                    binding.amountTextview.text = counter.toString()

                    item.quantity = counter

                    viewModel.editFromWishlist(item)
                }
            }

            Picasso.get().load(item.image).into(binding.favoritesImageview)


            binding.productNameWishlistTv.text = item.name




        }
    }
}