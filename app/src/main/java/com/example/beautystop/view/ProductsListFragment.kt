package com.example.beautystop.view

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beautystop.R
import com.example.beautystop.databinding.FragmentProductsListBinding
import com.example.beautystop.models.MakeupModel
import com.example.beautystop.view.adapter.ProductsAdapter

import com.google.android.material.tabs.TabLayout
import java.lang.Exception
import java.lang.NullPointerException

private const val TAG = "ProductsListFragment"
class ProductsListFragment : Fragment() {

    lateinit var notificationChannel: NotificationChannel
    private val channel_id = "notification"
    lateinit var builder: Notification.Builder
    private val description = "notification"
    lateinit var notificationManager: NotificationManager
    private lateinit var layoutMangerr: GridLayoutManager
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
        notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notification()
        obervers()



        productsListAdapter = ProductsAdapter(productsListViewModel,requireContext())
        binding.productslistRecyclerview.adapter = productsListAdapter




        layoutMangerr = GridLayoutManager(requireContext(),3)
        binding.productslistRecyclerview.layoutManager=layoutMangerr
        // paging
        var loading = true
        var pastVisiblesItems: Int
        var visibleItemCount: Int
        var totalItemCount: Int



        // pagination

        binding.productslistRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = binding.productslistRecyclerview.getChildCount()
                    totalItemCount = binding.productslistRecyclerview.layoutManager!!.getItemCount()
                    pastVisiblesItems =  layoutMangerr.findFirstCompletelyVisibleItemPosition()
                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            Log.d("...", "Last Item Wow!")
                            productsListViewModel.nextPage()
                            // Do pagination.. i.e. fetch new data
                            loading = true
                        }

                    }

                }
            }
        })





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


        binding.productslistRecyclerview


    }



    fun obervers(){
        productsListViewModel.makeupProductsLiveData.observe(viewLifecycleOwner,{
            it?.let {

                productsListAdapter.submitList(it)
                allProducts = it
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
                Log.d(TAG,"search")
                try {
                    productsListAdapter.submitList(

                        allProducts.filter {

                            it.name?.lowercase()!!.contains(query!!.lowercase())

                        })

                productsListAdapter.submitList(
                    allProducts.filter{
                        it.brand?.lowercase()!!.contains(query!!.lowercase())
                    }
                )

                } catch (e:Exception){
                    Toast.makeText(requireActivity(), "product not found", Toast.LENGTH_SHORT).show()
                }


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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.app_bar_bag -> {
                findNavController().navigate(R.id.action_productsListFragment_to_shoppingCartFragment)

            }
        }

        return super.onOptionsItemSelected(item)


    }





    override fun onDestroy() {
        super.onDestroy()

        productsListViewModel.makeupProductsLiveData.postValue(null)

        productsListViewModel.pagelist = mutableListOf()
        productsListViewModel.allList = listOf()

    }

    fun notification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = NotificationChannel(channel_id,description,NotificationManager.IMPORTANCE_HIGH)



            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(requireActivity(), channel_id)
                .setSmallIcon(R.drawable.splash)
                .setContentTitle("Alright!")
                .setContentText("Check out the new products")
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.splash))

        } else {
            builder = Notification.Builder(requireActivity())
                .setSmallIcon(R.drawable.splash)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.splash))
        }
        notificationManager.notify(1234, builder.build())
    }


}


