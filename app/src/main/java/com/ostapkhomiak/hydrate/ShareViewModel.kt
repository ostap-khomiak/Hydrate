package com.ostapkhomiak.hydrate


import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData



class ShareViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences("hydrate_pref", Context.MODE_PRIVATE)

    val weight = MutableLiveData<Double>().apply {
        value = sharedPreferences.getFloat("weight", 66f).toDouble()
    }
    val calculatedAmount = MutableLiveData<Int>().apply {
        value = weight.value?.div(22)?.toInt()?.times(1000)?.let { sharedPreferences.getInt("calculatedAmount", it) }
    }
    val isWeightInLB = MutableLiveData<Boolean>().apply {
        value = sharedPreferences.getBoolean("isWeightInLB", false)
    }
    val isManualAmount = MutableLiveData<Boolean>().apply {
        value = sharedPreferences.getBoolean("isManualAmount", false)
    }
    val manualAmount = MutableLiveData<Int>().apply {
        value = sharedPreferences.getInt("manualAmount", 3000)
    }
    val consumedWater = MutableLiveData<Int>().apply {
        value = sharedPreferences.getInt("consumedWater", 0)
    }


    fun setWeight(newWeight: Double) {
        weight.value = newWeight
        sharedPreferences.edit().putFloat("weight", newWeight.toFloat()).apply()
    }

    fun getWeight(): Double? {
        return weight.value
    }

    fun getCalculatedAmount(): Int? {
        return calculatedAmount.value
    }

    fun updateCalculatedAmount() {

        if (isWeightInLB.value!!) {
            calculatedAmount.value = (weight.value?.div(22)?.toInt()?.times(1000))
        } else {
            calculatedAmount.value = (weight.value?.div(48)?.toInt()?.times(1000))
        }
        sharedPreferences.edit().putInt("calculatedAmount", calculatedAmount.value!!).apply()
    }


    fun setIsWeightInLB(newWeightInLB: Boolean) {
        isWeightInLB.value = newWeightInLB
        sharedPreferences.edit().putBoolean("isWeightInLB", newWeightInLB).apply()
    }

    fun getIsWeightInLB(): Boolean? {
        return isWeightInLB.value
    }


    fun setIsManualAmount(newManualAmount: Boolean) {
        isManualAmount.value = newManualAmount
        sharedPreferences.edit().putBoolean("isManualAmount", newManualAmount).apply()
    }

    fun getIsManualAmount(): Boolean? {
        return isManualAmount.value
    }


    fun setManualAmount(newAmount: Int) {
        manualAmount.value = newAmount
        sharedPreferences.edit().putInt("manualAmount", newAmount).apply()
    }

    fun getManualAmount(): Int? {
        return manualAmount.value
    }


    fun addConsumedWater(drinkedWater: Int) {
        consumedWater.value = consumedWater.value?.plus(drinkedWater)
        sharedPreferences.edit().putInt("consumedWater", consumedWater.value!!).apply()
    }

    fun getConsumedWater(): Int? {
        return consumedWater.value
    }

}