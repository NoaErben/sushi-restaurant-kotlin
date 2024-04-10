package com.example.sushirestaurant

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity2 : AppCompatActivity() {

    private var isHebrew = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val changeLangButton = findViewById<Button>(R.id.change_language)
        changeLangButton.setOnClickListener {
            toggleLanguage()
            recreate()
        }

        val reserveSeatsButton = findViewById<Button>(R.id.reserve_seats)
        reserveSeatsButton.setOnClickListener {
            // Create an instance of the DialogFragment
            val dialogFragment = ReservationDialogFragment()

            // Show the DialogFragment
            dialogFragment.show(supportFragmentManager, "ReservationDialogFragment")
        }
    }

    private fun toggleLanguage() {
        isHebrew = !isHebrew
        val languageCode = if (isHebrew) "iw" else "en"
        setLocale(languageCode)

    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(updateBaseContextLocale(newBase))
    }

    private fun updateBaseContextLocale(context: Context): Context {
        val languageCode = if (isHebrew) "iw" else "en"
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }
}
