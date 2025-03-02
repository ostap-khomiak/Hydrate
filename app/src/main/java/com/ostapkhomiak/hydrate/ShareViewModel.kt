package com.ostapkhomiak.hydrate


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareViewModel : ViewModel() {
    val weight = MutableLiveData<Double>().apply { value = 65.0 }
    val amount = MutableLiveData<Int>().apply { value = 2500 }
    val weightInLB = MutableLiveData<Boolean>().apply { value = false }

    fun setWeight(newWeight: Double) {
        weight.value = newWeight
    }

    fun setAmount(newAmount: Int) {
        amount.value = newAmount
    }
    fun setWeightInLB(newBoolean: Boolean){
        weightInLB.value = newBoolean
    }
}