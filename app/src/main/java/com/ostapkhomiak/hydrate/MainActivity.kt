package com.ostapkhomiak.hydrate

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {

    lateinit var todayBtn : Button
    lateinit var historyBtn : Button
    lateinit var settingsBtn : Button
    var weight: Double = 65.0
    var amount: Int = 2500;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        todayBtn = findViewById(R.id.todayBtn)
        historyBtn = findViewById(R.id.historyBtn)
        settingsBtn = findViewById(R.id.settingsBtn)

        val mainFragment = MainFragment()
        val historyFragment = HistoryFragment()
        val settingsFragment = SettingsFragment()

        todayBtn.setOnClickListener { changeFragment(mainFragment) }
        historyBtn.setOnClickListener { changeFragment(historyFragment) }
        settingsBtn.setOnClickListener { changeFragment(settingsFragment) }



    }

    public fun setWeight(weight: Double){
        this.weight = weight
    }
    fun changeFragment(fragment: Fragment){
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragmentContainerView, fragment)
        ft.commit()
    }
}