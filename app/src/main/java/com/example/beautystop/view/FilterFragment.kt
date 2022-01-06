package com.example.beautystop.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import com.example.beautystop.R
import com.example.beautystop.models.PriceRangeModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.RangeSlider
import java.text.NumberFormat
import java.util.*

private const val TAG = "FilterFragment"
class FilterFragment : BottomSheetDialogFragment() {

    private val productsListViewModel: ProductsListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rangeSlider: RangeSlider = view.findViewById(R.id.sliderRange)

        rangeSlider.setLabelFormatter { value: Float ->

            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("USD")
            format.format(value.toDouble())
        }

        rangeSlider.addOnChangeListener(object : RangeSlider.OnChangeListener {

            override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {
                val values = rangeSlider.values
                Log.d("From", values[0].toString())
                Log.d("T0", values[1].toString())
            }
        })


        var savebutton: Button = view.findViewById(R.id.filer_save_button)


        savebutton.setOnClickListener{
            val values = rangeSlider.values
            val range = PriceRangeModel(values[0],values[1])
            productsListViewModel.priceRangeLiveData.postValue(range)

            dismiss()


        }
    }

}