package com.ostapkhomiak.hydrate


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareViewModel : ViewModel() {
    private val _weight = MutableLiveData<Double>(65.0)
    val weight: LiveData<Double> = _weight

    private val _amount = MutableLiveData<Int>(2500)
    val amount: LiveData<Int> = _amount

    fun setWeight(newWeight: Double) {
        _weight.value = newWeight
    }

    fun setAmount(newAmount: Int) {
        _amount.value = newAmount
    }
}