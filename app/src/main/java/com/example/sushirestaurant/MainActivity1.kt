package com.example.sushirestaurant

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity1 : AppCompatActivity() {
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the layout for this activity
        setContentView(R.layout.activity_main1)
        val slideRightAnim = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_right
        )

        val nigiri_img = findViewById<ImageView>(R.id.nigiri_icon)
        nigiri_img.startAnimation(slideRightAnim)

        // Use Handler to delay execution of code for 3000 milliseconds (3 seconds)
        handler.postDelayed({
            // Create an Intent to start MainActivity2
            val intent = Intent(this@MainActivity1, MainActivity2::class.java)

            // Start MainActivity2
            startActivity(intent)

            // Finish current activity to prevent user from going back to it
            finish()
        }, 2330)
    }
}