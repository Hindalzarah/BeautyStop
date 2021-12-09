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
//import com.example.beautystop.models.MakeupModel
//import com.squareup.picasso.Picasso
//
//class FavoritesAdapter() :
//    RecyclerView.Adapter<FavoritesAdapter.FavoritesHolder>() {
//
//    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MakeupModel>() {
//        override fun areItemsTheSame(oldItem: MakeupModel, newItem: MakeupModel): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//
//    override fun areContentsTheSame(oldItem: MakeupModel, newItem: MakeupModel): Boolean {
//        return oldItem == newItem
//    }
//}
//
//
//    private val differ = AsyncListDiffer(this,DIFF_CALLBACK)
//
//    fun submitList(list: List<MakeupModel>) {
//        differ.submitList(list)
//    }
//
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int,
//    ): FavoritesAdapter.FavoritesHolder {
//
//
//        val binding = FavoritesItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        return FavoritesHolder(binding)
//
//
//    }
//
//    override fun onBindViewHolder(holder: FavoritesHolder, position: Int) {
//        val item = differ.currentList[position]
//
//        holder.bind(item) }
//
//    override fun getItemCount(): Int {
//        return differ.currentList.size
//    }
//
//    class FavoritesHolder(val binding: FavoritesItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: MakeupModel) {
//
//            binding.deleteFavButton.setOnClickListener {  }
//            binding.plusButton.setOnClickListener {  }
//            binding.minusButton.setOnClickListener {  }
//
//            Picasso.get().load(item.imageLink).into(binding.favoritesImageview)
//
//        }
//    }
//}