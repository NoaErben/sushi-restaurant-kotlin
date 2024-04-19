package com.example.sushirestaurant


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

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

        val aboutUsButton = findViewById<Button>(R.id.about_us)
        aboutUsButton.setOnClickListener {
            // Start the AboutUs activity
            startActivity(Intent(this, AboutUs::class.java))
        }
    }
}
