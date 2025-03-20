package com.ostapkhomiak.hydrate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    private val shareViewModel: ShareViewModel by activityViewModels()
    var goalTextView: TextView? = null
    var goalAmount: Int = 0
    var progressTextView: TextView? = null
    var percentageTextView: TextView? = null
    var ConfirmWater: Button? = null
    var editTextNumber: EditText? = null
    var progressBar3: ProgressBar? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().setTheme(R.style.Theme_Hydrate)
        val view = inflater.inflate(R.layout.fragment_main, container, false)


        // ids
        goalTextView = view.findViewById(R.id.GoalTextView)
        progressTextView = view.findViewById(R.id.ProgressTextView)
        percentageTextView = view.findViewById(R.id.PercentageTextView)
        progressBar3 = view.findViewById(R.id.progressBar3)
        ConfirmWater = view.findViewById(R.id.ConfirmWater)
        editTextNumber = view.findViewById(R.id.editTextNumber)

        // listeners

        ConfirmWater?.setOnClickListener {
            val addedAmount = editTextNumber?.text.toString().toIntOrNull() ?: 0
            if (addedAmount > 0){
                waterButtonsListener(addedAmount)
            }

        }

        val smallMLBtn = view.findViewById<Button>(R.id.smallMLBtn)
        smallMLBtn.setOnClickListener {
            waterButtonsListener(100)
        }

        val mediumMLBtn = view.findViewById<Button>(R.id.mediumMLBtn)
        mediumMLBtn.setOnClickListener {
            waterButtonsListener(200)
        }

        val largeMLBtn = view.findViewById<Button>(R.id.largeMLBtn)
        largeMLBtn.setOnClickListener {
            waterButtonsListener(300)
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
        progressBar3?.setProgress(shareViewModel.getConsumedWater()!!, true)
    }

    fun updateGoal() {

        if (shareViewModel.getIsManualAmount() == true) {
            goalAmount = shareViewModel.getManualAmount()!!
        } else {
            goalAmount = shareViewModel.getCalculatedAmount()!!
        }
        goalTextView?.setText("of " + goalAmount + "ml goal")

    }

    fun waterButtonsListener(amount: Int) {
        shareViewModel.addConsumedWater(amount)
        saveToHistory(amount)
    }

    fun saveToHistory(amount: Int) {
        val historyManager = HistoryManager(requireContext())
        val currentHistory = historyManager.loadHistory()

        val sdfDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val sdfTime = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = sdfDate.format(Date())
        val time = sdfTime.format(Date())

        // replace icon based on amount
        val iconId = when {
            amount < 200 -> R.drawable.drop
            amount in 200..399 -> R.drawable.glass_of_water
            else -> R.drawable.water_bottle
        }

        val entry = WaterIntakeEntry(
            iconId = iconId,
            amount = amount,
            time = time,
            date = date
        )

        if (currentHistory.size >= 8) {
            currentHistory.removeAt(0) // remove oldest
        }

        currentHistory.add(entry)
        historyManager.saveHistory(currentHistory)
    }

}