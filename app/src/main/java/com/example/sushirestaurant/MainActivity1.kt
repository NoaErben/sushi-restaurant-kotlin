package com.example.sushirestaurant

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity1 : AppCompatActivity() {
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        val nigiri_img = findViewById<ImageView>(R.id.nigiri_icon)

        // Get the current locale
        val currentLocale = Locale.getDefault().language

        // Load the appropriate animation based on the locale
        val slideAnim = if (currentLocale == "he") {
            // Load the RTL animation for Hebrew
            AnimationUtils.loadAnimation(applicationContext, R.anim.slide_right_rtl)
        } else {
            // Load the default LTR animation for other languages
            AnimationUtils.loadAnimation(applicationContext, R.anim.slide_right)
        }

        nigiri_img.startAnimation(slideAnim)

        handler.postDelayed({
            val intent = Intent(this@MainActivity1, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }, 2350)
    }
}
