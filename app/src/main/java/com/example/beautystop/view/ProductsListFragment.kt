package com.example.beautystop.view

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.beautystop.R
import com.example.beautystop.databinding.FragmentProductsListBinding
import com.example.beautystop.models.MakeupModel
import com.example.beautystop.view.adapter.ProductsAdapter

import com.google.android.material.tabs.TabLayout


class ProductsListFragment : Fragment() {

    private lateinit var binding: FragmentProductsListBinding
    private val productsListViewModel: ProductsListViewModel by activityViewModels()
    private lateinit var productsListAdapter: ProductsAdapter
    private var allProducts = listOf<MakeupModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProductsListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        obervers()
        productsListAdapter = ProductsAdapter(productsListViewModel,requireContext())
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
            it?.let {
                productsListAdapter.submitList(it)
                binding.listProgressBar.animate().alpha(0f)
                productsListViewModel.makeupProductsLiveData.postValue(null)
            }

        })

        productsListViewModel.makeupProductsErrorLiveData.observe(viewLifecycleOwner, {
            it?.let{
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as androidx.appcompat.widget.SearchView

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                productsListAdapter.submitList(
                    allProducts.filter {
                        it.brand?.lowercase()!!.contains(query!!.lowercase())

                    }
                )
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                productsListAdapter.submitList(allProducts)
                return true
            }

        })

    }
}
