package com.ostapkhomiak.hydrate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels


class MainFragment : Fragment() {

    private val shareViewModel: ShareViewModel by activityViewModels()
    var goalTextView: TextView? = null
    var goalAmount: Int = 0
    var progressTextView: TextView? = null
    var percentageTextView: TextView? = null
    var ConfirmWater: Button? = null
    var editTextNumber: EditText? = null




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().setTheme(R.style.Theme_Hydrate)
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        goalTextView = view.findViewById(R.id.GoalTextView)
        progressTextView = view.findViewById(R.id.ProgressTextView)
        percentageTextView = view.findViewById(R.id.PercentageTextView)
        ConfirmWater = view.findViewById(R.id.ConfirmWater)
        editTextNumber = view.findViewById(R.id.editTextNumber)
        ConfirmWater?.setOnClickListener {
            shareViewModel.addConsumedWater(editTextNumber?.text.toString().toIntOrNull() ?: 0)
        }

        val smallMLBtn = view.findViewById<Button>(R.id.smallMLBtn)
        smallMLBtn.setOnClickListener {
            shareViewModel.addConsumedWater(100)
        }

        val mediumMLBtn = view.findViewById<Button>(R.id.mediumMLBtn)
        mediumMLBtn.setOnClickListener {
            shareViewModel.addConsumedWater(200)
        }

        val largeMLBtn = view.findViewById<Button>(R.id.largeMLBtn)
        largeMLBtn.setOnClickListener {
            shareViewModel.addConsumedWater(300)
        }




        // Observe ViewModel

        shareViewModel.manualAmount.observe(viewLifecycleOwner) {
            updateGoal()
        }

        shareViewModel.calculatedAmount.observe(viewLifecycleOwner) {
            updateGoal()
        }

        shareViewModel.consumedWater.observe(viewLifecycleOwner) {
            updateProgress()
        }



        return view
    }


    fun updateProgress(){
        progressTextView?.setText("${shareViewModel.getConsumedWater()}ml")
        if(shareViewModel.getIsManualAmount()!!) {
            percentageTextView?.setText("${shareViewModel.getConsumedWater()?.times(100)?.div(shareViewModel.getManualAmount()!!)}%")
        } else {
            percentageTextView?.setText("${shareViewModel.getConsumedWater()?.times(100)?.div(shareViewModel.getCalculatedAmount()!!)}%")

        }
    }

    fun updateGoal() {

        if (shareViewModel.getIsManualAmount() == true) {
            goalAmount = shareViewModel.getManualAmount()!!
        } else {
            goalAmount = shareViewModel.getCalculatedAmount()!!
        }
        goalTextView?.setText("of " + goalAmount + "ml goal")

    }

}