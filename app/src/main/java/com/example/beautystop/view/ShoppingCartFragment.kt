package com.example.beautystop.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.beautystop.R
import com.example.beautystop.models.WishlistModel
import com.example.beautystop.view.adapter.CartAdapter

class ShoppingCartFragment : Fragment() {

    lateinit var reyclerview: RecyclerView
    lateinit var adapter: CartAdapter
    private val viewModel: WishlistViewModel by activityViewModels()
    val model = listOf<WishlistModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reyclerview = view.findViewById(R.id.cart_recyclerciew)
        adapter = CartAdapter(model,viewModel,requireContext())
        reyclerview.adapter = adapter

    }
}

