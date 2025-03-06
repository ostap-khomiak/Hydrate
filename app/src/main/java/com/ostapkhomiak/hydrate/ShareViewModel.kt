package com.ostapkhomiak.hydrate


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareViewModel : ViewModel() {

    val weight = MutableLiveData<Double>().apply { value = 66.0 }
    val calculatedAmount =
        MutableLiveData<Int>().apply { value = (weight.value?.div(22)?.toInt()?.times(1000)) }
    val isWeightInLB = MutableLiveData<Boolean>().apply { value = false }
    val isManualAmount = MutableLiveData<Boolean>().apply { value = false }
    val manualAmount = MutableLiveData<Int>().apply { value = 3000 }
    val consumedWater = MutableLiveData<Int>().apply { value = 0}


    fun setWeight(newWeight: Double) {
        weight.value = newWeight
    }

    fun getWeight(): Double? {
        return weight.value
    }

    fun getCalculatedAmount(): Int? {
        return calculatedAmount.value
    }

    fun updateCalculatedAmount() {

        if (isWeightInLB.value == true) {
            calculatedAmount.value = (weight.value?.div(22)?.toInt()?.times(1000))
        } else {
            calculatedAmount.value = (weight.value?.div(48)?.toInt()?.times(1000))
        }
    }


    fun setIsWeightInLB(newWeightInLB: Boolean) {
        isWeightInLB.value = newWeightInLB
    }

    fun getIsWeightInLB(): Boolean? {
        return isWeightInLB.value
    }


    fun setIsManualAmount(newManualAmount: Boolean) {
        isManualAmount.value = newManualAmount
    }

    fun getIsManualAmount(): Boolean? {
        return isManualAmount.value
    }


    fun setManualAmount(newAmount: Int) {
        manualAmount.value = newAmount
    }

    fun getManualAmount(): Int? {
        return manualAmount.value
    }


    fun addConsumedWater(drinkedWater: Int) {
        consumedWater.value = consumedWater.value?.plus(drinkedWater)
    }

    fun getConsumedWater(): Int? {
        return consumedWater.value
    }

}