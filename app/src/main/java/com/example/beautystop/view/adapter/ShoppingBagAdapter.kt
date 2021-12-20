package com.example.beautystop.view.adapter
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.beautystop.R
import com.example.beautystop.models.ShoppingBagModel
import com.example.beautystop.view.ShoppingBagViewModel


class ShoppingBagAdapter(private val list: List<ShoppingBagModel>, val viewModel: ShoppingBagViewModel, val context: Context) :
    RecyclerView.Adapter<ShoppingBagAdapter.CartHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingBagAdapter.CartHolder {
        return CartHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.shopping_bag_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        val item = list[position]
         val quantity = item.quantity
        holder.productName.text = item.name
        holder.productPrice.text = item.price.toString()
       Glide.with(context).load(item.image).into(holder.productImage)

        val intent = Intent()
       Glide.with(context).load(intent.getStringExtra("product_image")).into(holder.productImage)
        holder.productName.text = intent.getStringExtra("product_name")
        holder.productPrice.text = intent.getStringExtra("product_price")


    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CartHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var productImage: ImageView = itemView.findViewById(R.id.cart_product_imageview)
        var productName: TextView = itemView.findViewById(R.id.cart_product_name_tv)
        var productPrice: TextView = itemView.findViewById(R.id.cart_product_price_tv)
        var deleteButton: ImageButton = itemView.findViewById(R.id.cart_delete_button)
    }
}