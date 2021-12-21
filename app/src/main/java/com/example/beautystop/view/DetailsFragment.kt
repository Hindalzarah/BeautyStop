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
import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.beautystop.R
import com.example.beautystop.models.ShoppingBagModel

private const val TAG = "DetailsFragment"
class DetailsFragment() : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var makeupModel: MakeupModel
    private val productViewModel: ProductsListViewModel by activityViewModels()
    private val detailsViewModel: DetailsViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observers()

        productViewModel.selectItem.observe(viewLifecycleOwner, Observer { value ->
            Picasso.get().load(value.imageLink).into(binding.productDetailsImageview)
            binding.name.text = value.brand
            binding.brand.text = value.name

            var counter = 1


          binding.detailsPlusButton.setOnClickListener{
              counter++
              binding.detailsAmountTextview.text = counter.toString()
          }
            binding.detailsMinusButton.setOnClickListener{
                if( binding.detailsAmountTextview.text != "0") {
                    counter--
                    binding.detailsAmountTextview.text = counter.toString()
                }
            }



            if(value.price == "0.0"){
                binding.price.text = "30"
            } else{

                binding.price.text = "${value.price} USD"
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

            Log.d(TAG,makeupModel.toString())
        })

        binding.favoriteToggleButton.setOnClickListener {

            if(binding.favoriteToggleButton.isChecked){
                productViewModel.addToWishlist(makeupModel)
            }


        }

        binding.addtoCartButton.setOnClickListener{
            detailsViewModel.addToShoppingBag(makeupModel,binding.detailsAmountTextview.text.toString().toInt())
        }


    }

    fun observers(){

        detailsViewModel.makeupProductsLiveData.observe(viewLifecycleOwner,{
            it?.let {

//                binding.listProgressBar.animate().alpha(0f)

                detailsViewModel.makeupProductsLiveData.postValue(null)
            }
        })

        detailsViewModel.makeupProductsErrorLiveData.observe(viewLifecycleOwner, {
            it?.let{
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }



}
