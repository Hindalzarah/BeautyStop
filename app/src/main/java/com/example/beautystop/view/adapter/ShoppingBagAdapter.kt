package com.example.beautystop.view.adapter

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.beautystop.R
import com.example.beautystop.models.ShoppingBagModel
import com.example.beautystop.view.ShoppingBagViewModel


class ShoppingBagAdapter(
    var list: MutableList<ShoppingBagModel>,
    val viewModel: ShoppingBagViewModel,
    val context: Context,
) :
    RecyclerView.Adapter<ShoppingBagAdapter.CartHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ShoppingBagAdapter.CartHolder {
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
//setting the values of the views and assigning them to the data from the model "api"
        holder.productName.text = item.name
        holder.productPrice.text = item.price.toString()
//        Glide.with(context).load(item.image).into(holder.productImage)
        Glide.with(context).load(item.image).into(holder.productImage)
        holder.productName.text = item.name
        //handling the items that has null price that comes from the api
        if (item.price != 0.0) {
            holder.productPrice.text = "${item.price}$"
        } else {
            holder.productPrice.text =  "15$"
        }
        holder.quantity.text = "Quantity: ${item.quantity}"


        holder.deleteButton.setOnClickListener {
            //deleting the data from the model.
            list.remove(item)
            notifyDataSetChanged()
            //http functions that deletes data from the api.
            viewModel.deleteFromShoppingBag(item.id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CartHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var productImage: ImageView = itemView.findViewById(R.id.cart_product_imageview)
        var productName: TextView = itemView.findViewById(R.id.cart_product_name_tv)
        var productPrice: TextView = itemView.findViewById(R.id.cart_product_price_tv)
        var quantity: TextView = itemView.findViewById(R.id.cart_product_quantity_tv)
        var deleteButton: ImageButton = itemView.findViewById(R.id.cart_delete_button)
    }
}