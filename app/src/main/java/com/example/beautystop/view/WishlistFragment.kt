package com.example.beautystop.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.beautystop.databinding.FragmentWishlistBinding
import com.example.beautystop.view.adapter.WishlistAdapter

class WishlistFragment : Fragment() {

    private lateinit var binding: FragmentWishlistBinding
    private lateinit var wishlistAdapter: WishlistAdapter
    val wishlistViewModel: WishlistViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWishlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observrs()

        wishlistAdapter = WishlistAdapter(wishlistViewModel)

        binding.wishlistRecyclerview.adapter = wishlistAdapter


        wishlistViewModel.callWishlist()



    }



    fun observrs() {

        wishlistViewModel.wishlistLiveData.observe(viewLifecycleOwner, Observer {
            wishlistAdapter.submitList(it)

            binding.favoritesProgressBar.animate().alpha(0f)
        })

        wishlistViewModel.wishlistErrorLiveData.observe(viewLifecycleOwner, Observer {
            it?.let{
                Toast.makeText(requireActivity(), "$it wish", Toast.LENGTH_SHORT).show()
            }
        })

    }
}