/**
 * This class represents the main activity of the application.
 * It displays options for the user to observe the menu, reserve seats or view information about the restaurant.
 */

package com.example.sushirestaurant

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Load the jiggle animation
        val jiggleAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.jiggle_animation)

        // Find the "Reserve Seats" button in the layout
        val reserveSeatsButton = findViewById<Button>(R.id.reserve_seats_button)

        // Apply the jiggle animation to the "Reserve Seats" button
        reserveSeatsButton.startAnimation(jiggleAnimation)

        // Set a click listener for the "Reserve Seats" button
        reserveSeatsButton.setOnClickListener {
            // Create an instance of the DialogFragment
            val dialogFragment = ReservationDialogFragment()

            // Show the DialogFragment
            dialogFragment.show(supportFragmentManager, "ReservationDialogFragment")
        }

        // Find the "Our View" button in the layout
        val ourViewButton = findViewById<Button>(R.id.our_view_button)

        // Set a click listener for the "Our View" button
        ourViewButton.setOnClickListener {
            // Start the OurVision activity
            startActivity(Intent(this, OurVision::class.java))
        }
    }
}
