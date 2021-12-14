package com.example.beautystop.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.beautystop.R
import com.example.beautystop.databinding.FragmentCategoryMainBinding
import com.example.beautystop.databinding.FragmentProductsListBinding
//import com.example.beautystop.view.identity.LoginActivity
//import com.example.beautystop.view.identity.SHARED_PREF_FILE
import com.google.firebase.auth.FirebaseAuth

class CategoryMainFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var sharedPrefEditor: SharedPreferences.Editor
    private lateinit var binding: FragmentCategoryMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCategoryMainBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //creating an object of Bundle to help pass the right data
        val bundle = Bundle()

        //if user clicked on face CardView
        binding.faceCardview.setOnClickListener {
            //saving the data in bundle
            bundle.putString("Type", "Face")
            findNavController().navigate(R.id.action_categoryMainFragment_to_productsListFragment,
                bundle)
        }


        //if user clicked on lips CardView
        binding.lipsCardview.setOnClickListener {
            bundle.putString("Type", "Lips")
            findNavController().navigate(R.id.action_categoryMainFragment_to_productsListFragment,
                bundle)
        }
        //if user clicked on eyes CardView
        binding.eyesCardview.setOnClickListener {
            bundle.putString("Type", "Eyes")
            findNavController().navigate(R.id.action_categoryMainFragment_to_productsListFragment,
                bundle)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.main_menu, menu)

        val logout = menu.findItem(R.id.logout)

        logout.setOnMenuItemClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            sharedPref = requireActivity().getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
            sharedPrefEditor = sharedPref.edit()
            sharedPrefEditor.putBoolean("isLogged?", false)
            sharedPrefEditor.commit()
            startActivity(intent)

                true
        }

    }


}