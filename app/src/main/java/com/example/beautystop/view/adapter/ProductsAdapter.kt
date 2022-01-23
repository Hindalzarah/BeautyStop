package com.example.beautystop.view.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.beautystop.R
import com.example.beautystop.databinding.ProductsItemLayoutBinding
import com.example.beautystop.models.MakeupModel
import com.example.beautystop.view.ProductsListViewModel

class ProductsAdapter(val productViewModel: ProductsListViewModel, val context: Context) :
    RecyclerView.Adapter<ProductsAdapter.ProductsHolder>() {


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MakeupModel>() {
        override fun areItemsTheSame(oldItem: MakeupModel, newItem: MakeupModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MakeupModel, newItem: MakeupModel): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: List<MakeupModel>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsAdapter.ProductsHolder {

        val binding =
            ProductsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsAdapter.ProductsHolder(binding, context)

    }

    override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
        val item = differ.currentList[position]

        holder.bind(item, productViewModel)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class ProductsHolder(val binding: ProductsItemLayoutBinding, val context: Context) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MakeupModel, productViewModel: ProductsListViewModel) {
            Glide.with(context).load(item.imageLink).placeholder(R.drawable.splash)
                .into(binding.imageView)

            //setting the values of the views and assigning them to the data from the model "api"
            binding.listBrand.text = item.brand
            binding.listName.text = item.name
            //handling the items that has null price that comes from the api
            if (item.price == "0.0") {
                binding.priceTv.text = "15$"
            } else {
                binding.priceTv.text = "${item.price}$"
            }

            /* posting the selected item data in the livedata, navigating to the details fragment when
            the user clicks on an item */
            binding.imageView.setOnClickListener() {
                productViewModel.selectItem.postValue(item)
                it.findNavController().navigate(R.id.action_productsListFragment_to_detailsFragment)

            }
        }
    }
}