package com.ostapkhomiak.hydrate

import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private val shareViewModel: ShareViewModel by viewModels()

    lateinit var todayBtn : Button
    lateinit var historyBtn : Button
    lateinit var settingsBtn : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1002)
            }
        }


        checkAndResetDailyProgress()
        scheduleReminder()

        // load notifications preferences
        val prefs = getSharedPreferences("daily_prefs", MODE_PRIVATE)
        val notifEnabled = prefs.getBoolean("notifications_enabled", true)
        shareViewModel.setIsNotificationEnabled(notifEnabled)
        if (notifEnabled) {
            scheduleReminder()
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

    fun changeFragment(fragment: Fragment){
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragmentContainerView, fragment)
        ft.commit()
    }

    fun scheduleReminder() {
        val workRequest = PeriodicWorkRequestBuilder<WaterReminderWorker>(
            3, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "WaterReminder",
            androidx.work.ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun checkAndResetDailyProgress() {
        val prefs = getSharedPreferences("daily_prefs", MODE_PRIVATE)
        val lastOpenedDate = prefs.getString("last_date", null)

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val todayDate = sdf.format(Date())

        if (lastOpenedDate != todayDate) {
            // reset progress everyday
            val editor = prefs.edit()
            editor.putString("last_date", todayDate)
            editor.apply()


            resetProgress(prefs)
        }
    }

    fun resetProgress(prefs : SharedPreferences = getSharedPreferences("daily_prefs", MODE_PRIVATE)) {
        shareViewModel.resetConsumedWater()

        // Clear history
        val historyManager = HistoryManager(this)
        historyManager.saveHistory(emptyList())
    }

    fun handleNotificationToggle(enabled: Boolean) {
        if (enabled) {
            scheduleReminder()
        } else {
            WorkManager.getInstance(this).cancelUniqueWork("WaterReminder")
        }
    }
}