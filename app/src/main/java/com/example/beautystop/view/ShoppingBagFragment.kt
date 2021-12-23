package com.example.beautystop.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.beautystop.R
import com.example.beautystop.models.ShoppingBagModel
import com.example.beautystop.view.adapter.ShoppingBagAdapter

class ShoppingBagFragment : Fragment() {

    lateinit var reyclerview: RecyclerView
    lateinit var adapter: ShoppingBagAdapter
    private val viewModel: ShoppingBagViewModel by activityViewModels()
    var model = mutableListOf<ShoppingBagModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_bag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observers()
        reyclerview = view.findViewById(R.id.cart_recyclerciew)
        Log.d("ShoppingFragment",model.toString())
        adapter = ShoppingBagAdapter(model,viewModel,requireContext())
        reyclerview.adapter = adapter
        viewModel.callShoppingBag()


    }


    fun observers() {
        viewModel.shoppingBagLiveData.observe(viewLifecycleOwner, {


            it?.let {
               // model = it
               // Log.d("model",model.toString())
                adapter.list = it.toMutableList()
                //adapter = ShoppingBagAdapter(it,viewModel,requireContext())
                Log.d("ShoppingFragment",it.toString())

                adapter.notifyDataSetChanged()

            }


        })

        viewModel.shoppingBagErrorLiveData.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

