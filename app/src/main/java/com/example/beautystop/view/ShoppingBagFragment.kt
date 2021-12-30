package com.example.beautystop.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.beautystop.R
import com.example.beautystop.models.ShoppingBagModel
import com.example.beautystop.view.adapter.ShoppingBagAdapter

class ShoppingBagFragment : Fragment() {


    lateinit var adapter: ShoppingBagAdapter
    private val viewModel: ShoppingBagViewModel by activityViewModels()
    var model = mutableListOf<ShoppingBagModel>()
    lateinit var recyclerView: RecyclerView
    lateinit var emptyTextView: TextView
    lateinit var orderButton: Button
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
        recyclerView = view.findViewById(R.id.cart_recyclerciew)

        Log.d("ShoppingFragment", model.toString())
        adapter = ShoppingBagAdapter(model, viewModel, requireContext())
        recyclerView.adapter = adapter
        viewModel.callShoppingBag()
        emptyTextView = view.findViewById(R.id.empty_shoppingbag_tv)

        orderButton = view.findViewById(R.id.order_button)

        orderButton.setOnClickListener {

            if (recyclerView.isNotEmpty()) {
                findNavController().navigate(R.id.action_shoppingCartFragment_to_orderFragment)
            } else {
                Toast.makeText(requireContext(), "your bag is empty :(", Toast.LENGTH_SHORT).show()
            }

            adapter.list.clear()
            adapter.list.removeAll(model)
            adapter.notifyDataSetChanged()
            model.forEach {
                viewModel.deleteFromShoppingBag(it.id)
            }

        }

    }


    fun observers() {
        viewModel.shoppingBagLiveData.observe(viewLifecycleOwner, {


            it?.let {
                model = it.toMutableList()
                // Log.d("model",model.toString())
                adapter.list = it.toMutableList()
                //adapter = ShoppingBagAdapter(it,viewModel,requireContext())
                Log.d("ShoppingFragment", it.toString())

                adapter.notifyDataSetChanged()

            }


            if (it.isEmpty()) {
                emptyTextView.isVisible = true
                orderButton.isVisible = false

            } else {
                emptyTextView.isVisible = false
                orderButton.isVisible = true
            }


        })

        viewModel.shoppingBagErrorLiveData.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

