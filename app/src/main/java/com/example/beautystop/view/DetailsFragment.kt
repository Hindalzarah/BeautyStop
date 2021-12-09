package com.example.beautystop.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beautystop.R
import com.example.beautystop.databinding.FragmentCategoryMainBinding
import com.example.beautystop.databinding.FragmentDetailsBinding
import com.example.beautystop.databinding.FragmentProductsListBinding
import com.example.beautystop.models.MakeupModel

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
   private lateinit var makeupModel: MakeupModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.brand.text = makeupModel.brand
        binding.name.text = makeupModel.name
        binding.price.text = makeupModel.price
        binding.website.text = makeupModel.websiteLink
        binding.productDetailsImageview





    }


}