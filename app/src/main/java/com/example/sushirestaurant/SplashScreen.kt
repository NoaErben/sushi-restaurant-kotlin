/**
 * This class represents the splash screen of the application. It displays a nigiri with animation
 * that reveals the restaurant's name, then navigates to MainActivity after a short delay.
 */

package com.example.sushirestaurant

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    // Handler for delaying the navigation
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        // Reference to the ImageView displaying the nigiri image
        val nigiri_img = findViewById<ImageView>(R.id.nigiri_icon)

        // Load slide animation from XML
        val slideAnim = AnimationUtils.loadAnimation(applicationContext, R.anim.slide_right_splash)

        // Start animation on the ImageView
        nigiri_img.startAnimation(slideAnim)

        // Delayed navigation to MainActivity
        handler.postDelayed({
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2350)
    }
}
