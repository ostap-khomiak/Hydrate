package com.ostapkhomiak.hydrate


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareViewModel : ViewModel() {
    val weight = MutableLiveData<Double>().apply { value = 65.0 }
    val amount = MutableLiveData<Int>().apply { value = 2500 }
    val weightInLB = MutableLiveData<Boolean>().apply { value = false }
    val manualAmount = MutableLiveData<Boolean>().apply { value = false }
    val consumedWater = MutableLiveData<Int>()


    fun setWeight(newWeight: Double) {
        weight.value = newWeight
    }
    fun getWeight(): Double?{
        return weight.value
    }

    fun setAmount(newAmount: Int) {
        amount.value = newAmount
    }
    fun getAmount(): Int? {
        return amount.value
    }

    fun setWeightInLB(newWeightInLB: Boolean){
        weightInLB.value = newWeightInLB
    }
    fun getWeightInLB(): Boolean? {
        return weightInLB.value
    }

    fun setManualAmount(newManualAmount: Boolean) {
        manualAmount.value = newManualAmount
    }
    fun getManualAmount(): Boolean? {
        return manualAmount.value
    }

    fun setConsumedWater(newConsumedWater: Int){
        consumedWater.value = newConsumedWater
    }
    fun getConsumedWater(): Int? {
        return consumedWater.value
    }

}