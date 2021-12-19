package com.example.beautystop.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.beautystop.databinding.FragmentDetailsBinding
import com.example.beautystop.models.MakeupModel
import com.squareup.picasso.Picasso
import android.widget.Toast
import android.content.ActivityNotFoundException
import android.net.Uri
import androidx.navigation.fragment.findNavController
import com.example.beautystop.R


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
            binding.name.text = value.brand
            binding.brand.text = value.name

            if(value.price == "0.0"){
                binding.price.text = "Not Available"
            } else{

                binding.price.text = "${value.price} USD"
            }

           binding.addtoCartButton.setOnClickListener(){

               val intent = Intent()
               val price = binding.price.text
               val name = binding.name.text


               intent.putExtra("product_price",price)
               intent.putExtra("product_name",name)

               findNavController().navigate(R.id.action_detailsFragment_to_shoppingCartFragment)
           }

            binding.website.setOnClickListener() {
                try {
                    val myIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(value.productLink.toString()))
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



}
