package com.example.sushirestaurant

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity2 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)



        val reserveSeatsButton = findViewById<Button>(R.id.reserve_seats)
        reserveSeatsButton.setOnClickListener {
            // Create an instance of the DialogFragment
            val dialogFragment = ReservationDialogFragment()

            // Show the DialogFragment
            dialogFragment.show(supportFragmentManager, "ReservationDialogFragment")
        }
    }



    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }



}
