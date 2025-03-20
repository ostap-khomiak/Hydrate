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
        val temp = (weight.value!! / 22).toInt() * 1000
        value = temp
        sharedPreferences.getInt("calculatedAmount", temp)
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
    val isNotificationEnabled = MutableLiveData<Boolean>().apply {
        value = sharedPreferences.getBoolean("isNotificationEnabled", true)
    }


    fun setWeight(newWeight: Double) {
        weight.value = newWeight
        sharedPreferences.edit().putFloat("weight", newWeight.toFloat()).apply()
        updateCalculatedAmount()
    }

    fun getWeight(): Double? {
        return weight.value
    }

    fun getCalculatedAmount(): Int? {
        return calculatedAmount.value
    }

    fun updateCalculatedAmount() {

        if (isWeightInLB.value!!) {
            calculatedAmount.value = (weight.value!! / 22 * 1000).toInt()  // TODO: fix ml calculation
        } else {
            calculatedAmount.value = (weight.value!! / 48 * 1000).toInt()
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

    fun resetConsumedWater(){
        consumedWater.value = 0
        sharedPreferences.edit().putInt("consumedWater", 0).apply()
    }

    fun setIsNotificationEnabled(enabled: Boolean) {
        isNotificationEnabled.value = enabled
        sharedPreferences.edit().putBoolean("isNotificationEnabled", enabled).apply()
    }

    fun getIsNotificationEnabled(): Boolean? {
        return isNotificationEnabled.value
    }


}