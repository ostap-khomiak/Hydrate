package com.ostapkhomiak.hydrate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import android.widget.EditText
import android.widget.CheckBox
import android.widget.Switch


class SettingsFragment : Fragment() {

    private val shareViewModel: ShareViewModel by activityViewModels()


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().setTheme(R.style.Theme_Hydrate)
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val weightEditText = view.findViewById<EditText>(R.id.editWeightTextNumber)
        val manualWaterText = view.findViewById<EditText>(R.id.manualWaterTextNumber)
        val disableCalcCheckBox = view.findViewById<CheckBox>(R.id.disableCalcCheckBox)
        val kglbswitch = view.findViewById<Switch>(R.id.kglbswitch)
        kglbswitch.setText("KG")

        // Observe ViewModel
        shareViewModel.weight.observe(viewLifecycleOwner) { weight ->
            weightEditText.setText(weight.toString())
        }

        shareViewModel.amount.observe(viewLifecycleOwner) { amount ->
            manualWaterText.setText(amount.toString())
        }
        shareViewModel.weightInLB.observe(viewLifecycleOwner){ weightInLB ->
            if(weightInLB){
                kglbswitch.setText("LB")
            } else {
                kglbswitch.setText("KG")
            }
        }


        // Update ViewModel
        weightEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                shareViewModel.setWeight(weightEditText.text.toString().toDoubleOrNull() ?: 65.0)
            }
        }

        manualWaterText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                shareViewModel.setAmount(manualWaterText.text.toString().toIntOrNull() ?: 2500)
            }
        }

        kglbswitch.setOnClickListener {
            shareViewModel.setWeightInLB(kglbswitch.isChecked)
        }

        return view

    }


}