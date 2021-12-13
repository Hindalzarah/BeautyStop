package com.example.beautystop.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

import com.example.beautystop.databinding.FragmentCategoryMainBinding
import com.example.beautystop.databinding.FragmentDetailsBinding
import com.example.beautystop.databinding.FragmentProductsListBinding
import com.example.beautystop.models.MakeupModel
import com.example.beautystop.view.adapter.ProductsListViewModel
import com.squareup.picasso.Picasso
import android.text.method.LinkMovementMethod

import android.R

import android.widget.TextView
import android.widget.Toast

import android.content.ActivityNotFoundException
import android.net.Uri
import com.example.beautystop.models.WishlistModel
import okhttp3.internal.userAgent


class DetailsFragment() : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var makeupModel: MakeupModel
    val productViewModel: ProductsListViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        productViewModel.selectItem.observe(viewLifecycleOwner, Observer { value ->
            Picasso.get().load(value.imageLink).into(binding.productDetailsImageview)
            binding.name.text = value.name
            binding.brand.text = value.brand
            binding.price.text = "${value.price} SAR"

            binding.website.setOnClickListener() {
                try {
                    val myIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(value.websiteLink.toString()))
                                startActivity(myIntent)
                } catch (e: ActivityNotFoundException) {

                    Toast.makeText(
                        requireActivity(),
                        "No application can handle this request",
                        Toast.LENGTH_SHORT
                    ).show()
                    e.printStackTrace()
                }
            }


            makeupModel = value
        })



        binding.favoriteToggleButton.setOnClickListener {

            if(binding.favoriteToggleButton.isChecked){
                productViewModel.addToWishlist(makeupModel)
            }
        }

    }
//    fun MakeupModel.toWishlistModel()=WishlistModel(
//
//        image = imageLink.toString(),
//        id = id.toString(),
//        name = name.toString(),
//        quantity =
//    )


}
