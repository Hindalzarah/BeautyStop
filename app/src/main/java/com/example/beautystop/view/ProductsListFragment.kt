package com.example.beautystop.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.beautystop.R
import com.example.beautystop.databinding.FragmentProductsListBinding
import com.example.beautystop.view.adapter.ProductsAdapter
import com.example.beautystop.view.adapter.ProductsListViewModel
import com.google.android.material.tabs.TabLayout


class ProductsListFragment : Fragment() {

    private lateinit var binding: FragmentProductsListBinding

    private val productsListViewModel: ProductsListViewModel by activityViewModels()
    private lateinit var productsListAdapter: ProductsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProductsListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        obervers()
        productsListAdapter = ProductsAdapter()
        binding.productslistRecyclerview.adapter = productsListAdapter


        Log.d("productlistFragment","`test")

        //getting the data from bundle
        val product_type = arguments?.getString("Type")
       //null safety
        product_type?.let{
            Log.d("productlistFragment",product_type)

            //when to check for multiple arguments
            when(product_type){
                //when the user clicks on Face get the foundation data from the api
                "Face" ->  productsListViewModel.callMakeupProducts("foundation")
                "Eyes" -> productsListViewModel.callMakeupProducts("eyeshadow")
                "Lips" -> productsListViewModel.callMakeupProducts("lipstick")
            }



        }




    }



    fun obervers(){
        productsListViewModel.makeupProductsLiveData.observe(viewLifecycleOwner,{
           productsListAdapter.submitList(it)
        })

        productsListViewModel.makeupProductsErrorLiveData.observe(viewLifecycleOwner, {
            it?.let{
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}