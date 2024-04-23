package com.example.sushirestaurant

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

//        val btnSlideLeft = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_left)
//        val btnSlideRight = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_right_splash)
        val jiggleAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.jiggle_animation)

        val reserveSeatsButton = findViewById<Button>(R.id.reserve_seats_button)
        // Apply jiggle animation to the button
        reserveSeatsButton.startAnimation(jiggleAnimation)
        
        reserveSeatsButton.setOnClickListener {
            // Create an instance of the DialogFragment
            val dialogFragment = ReservationDialogFragment()

            // Show the DialogFragment
            dialogFragment.show(supportFragmentManager, "ReservationDialogFragment")
        }

        val aboutUsButton = findViewById<Button>(R.id.our_view_button)
        aboutUsButton.setOnClickListener {
            // Start the AboutUs activity
            startActivity(Intent(this, OurVision::class.java))
        }
    }
}
