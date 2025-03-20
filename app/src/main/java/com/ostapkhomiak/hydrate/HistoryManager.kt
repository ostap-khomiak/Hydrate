package com.ostapkhomiak.hydrate

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryManager(context: Context) {
    private val prefs = context.getSharedPreferences("water_history", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveHistory(historyList: List<WaterIntakeEntry>) {
        val json = gson.toJson(historyList)
        prefs.edit().putString("history_list", json).apply()
    }

    fun loadHistory(): MutableList<WaterIntakeEntry> {
        val json = prefs.getString("history_list", null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<WaterIntakeEntry>>() {}.type
        return gson.fromJson(json, type)
    }
}