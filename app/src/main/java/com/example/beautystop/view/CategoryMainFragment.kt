package com.example.beautystop.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.beautystop.R
import com.example.beautystop.view.identity.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class CategoryMainFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_main, container, false)



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.main_menu, menu)

        val logout = menu.findItem(R.id.logout)

        logout.setOnMenuItemClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(requireActivity(), LoginActivity:: class.java)

            startActivity(intent)
            true
        }
    }

}