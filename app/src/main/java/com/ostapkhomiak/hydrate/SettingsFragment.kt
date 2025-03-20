package com.ostapkhomiak.hydrate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import android.widget.EditText
import android.widget.CheckBox
import android.widget.Switch


class SettingsFragment : Fragment() {

    private val shareViewModel: ShareViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        requireActivity().setTheme(R.style.Theme_Hydrate)
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // ids
        val weightEditText = view.findViewById<EditText>(R.id.editWeightTextNumber)
        weightEditText.setText(shareViewModel.getWeight().toString())


        val disableCalcCheckBox = view.findViewById<CheckBox>(R.id.disableCalcCheckBox)
        val manualWaterText = view.findViewById<EditText>(R.id.manualWaterTextNumber)

        val kglbswitch = view.findViewById<Switch>(R.id.kglbswitch)
        kglbswitch.setText("KG")

        val notificationsCheckBox = view.findViewById<CheckBox>(R.id.notificationsCheckBox)
        notificationsCheckBox.isChecked = shareViewModel.getIsNotificationEnabled() ?: true

        val deleteButton = view.findViewById<Button>(R.id.deleteButton)



        // Observe ViewModel
        shareViewModel.weight.observe(viewLifecycleOwner) { weight ->
            weightEditText.setText(weight.toString())
        }

        if (shareViewModel.getIsManualAmount()!!) {
            shareViewModel.manualAmount.observe(viewLifecycleOwner) { amount ->
                manualWaterText.setText(amount.toString())
            }
        }

        shareViewModel.isManualAmount.observe(viewLifecycleOwner) {
            if (shareViewModel.getIsManualAmount()!!) {
                manualWaterText.setVisibility(View.VISIBLE)
            } else {
                manualWaterText.setVisibility(View.INVISIBLE)
            }
        }

        shareViewModel.isWeightInLB.observe(viewLifecycleOwner) { weightInLB ->
            if (weightInLB) {
                kglbswitch.setText("LB")
            } else {
                kglbswitch.setText("KG")
            }
        }

        shareViewModel.isNotificationEnabled.observe(viewLifecycleOwner) {
            notificationsCheckBox.isChecked = it
        }



        // Update ViewModel
        weightEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                shareViewModel.setWeight(weightEditText.text.toString().toDoubleOrNull() ?: 66.0)
            }
        }

        manualWaterText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                shareViewModel.setManualAmount(
                    manualWaterText.text.toString().toIntOrNull() ?: 3000
                )
            }
        }

        kglbswitch.setOnClickListener {
            shareViewModel.setIsWeightInLB(kglbswitch.isChecked)
        }

        disableCalcCheckBox.setOnClickListener {
            shareViewModel.setIsManualAmount(disableCalcCheckBox.isChecked)
            shareViewModel.updateCalculatedAmount()
        }

        notificationsCheckBox.setOnClickListener {
            shareViewModel.setIsNotificationEnabled(notificationsCheckBox.isChecked)
            // Save preference
            val prefs = requireContext().getSharedPreferences("daily_prefs", Context.MODE_PRIVATE)
            prefs.edit().putBoolean("notifications_enabled", notificationsCheckBox.isChecked).apply()

            // Trigger scheduling/canceling
            (activity as MainActivity).handleNotificationToggle(notificationsCheckBox.isChecked)
        }

        deleteButton.setOnClickListener {
            (activity as MainActivity).resetProgress()
        }

        return view

    }


}