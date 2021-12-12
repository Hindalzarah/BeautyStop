package com.example.beautystop.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beautystop.R
import com.example.beautystop.databinding.FragmentDetailsBinding
import com.example.beautystop.databinding.FragmentWishlistBinding
import com.example.beautystop.view.adapter.ProductsAdapter
import com.example.beautystop.view.adapter.WishlistAdapter

class WishlistFragment : Fragment() {

    private lateinit var binding: FragmentWishlistBinding
    private lateinit var wishlistAdapter: WishlistAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        wishlistAdapter = WishlistAdapter()

        binding.wishlistRecyclerview.adapter = wishlistAdapter





    }



    fun observrs() {


    }
}