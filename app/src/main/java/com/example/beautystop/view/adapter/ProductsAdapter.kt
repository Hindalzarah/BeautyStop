//package com.example.beautystop.view.adapter
//
//import androidx.recyclerview.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.AsyncListDiffer
//import androidx.recyclerview.widget.DiffUtil
//import com.example.beautystop.R
//import com.example.beautystop.databinding.FavoritesItemLayoutBinding
//import com.example.beautystop.databinding.ProductsItemLayoutBinding
//import com.example.beautystop.models.MakeupModel
//
//class ProductsAdapter(private val list: List<MakeupModel>) :
//    RecyclerView.Adapter<ProductsAdapter.ProductsHolder>() {
//
//
//    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MakeupModel>() {
//        override fun areItemsTheSame(oldItem: MakeupModel, newItem: MakeupModel): Boolean {
//            return oldItem.id == newItem.id
//        }
//        override fun areContentsTheSame(oldItem: MakeupModel, newItem: MakeupModel): Boolean {
//            return oldItem == newItem
//        }
//
//    }
//
//    private val differ = AsyncListDiffer(this,DIFF_CALLBACK)
//
//    fun submitList(list: List<MakeupModel>) {
//        differ.submitList(list)
//    }
//
//
//        override fun onCreateViewHolder(
//            parent: ViewGroup,
//            viewType: Int,
//        ): ProductsAdapter.ProductsHolder {
//
//            val binding = ProductsItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//            return ProductsAdapter.ProductsHolder(binding)
//
//        }
//
//        override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
//            val item = differ.currentList[position]
//
//            holder.bind(item)
//        }
//
//        override fun getItemCount(): Int {
//            return differ.currentList.size
//        }
//
//            class ProductsHolder(val binding: ProductsItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
//
//                fun bind(item: MakeupModel) {
//
//                    TODO()
//                }
//        }
//
//
//
//
//}
