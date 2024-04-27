/**
 * This class represents the Our Vision screen of the application. It displays information about
 * the restaurant's vision and details, and animations related to it.
 */
package com.example.sushirestaurant

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class OurVision : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.our_vision)

        // Load animation objects for each animation
        val fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        val fadeOut = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out)
        val bounceVision = AnimationUtils.loadAnimation(applicationContext, R.anim.bounce)
        val zoomInStar = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_in)
        val zoomOutStar = AnimationUtils.loadAnimation(applicationContext, R.anim.zoom_out)
        val rotateLeftEye = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate)
        val rotateRightEye = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate)


        // Set the back button
        val aboutUsButton = findViewById<Button>(R.id.back_button)
        aboutUsButton.setOnClickListener {
            // Start the OurVision activity
            startActivity(Intent(this, MainActivity::class.java))
        }


        // Start in the same time the zoom in animation
        val visionLabel = findViewById<TextView>(R.id.vision_label)
        visionLabel.startAnimation(bounceVision)


        //starts animation
        val zoomStar1 = findViewById<ImageView>(R.id.star1)
        zoomStar1.startAnimation(zoomOutStar)
        val zoomStar2 = findViewById<ImageView>(R.id.star2)
        zoomStar2.startAnimation(zoomOutStar)

        Handler().postDelayed({
            zoomStar1.startAnimation(zoomInStar)
            zoomStar2.startAnimation(zoomInStar)
        }, 1000)

        Handler().postDelayed({
            zoomStar1.startAnimation(fadeIn)
            zoomStar2.startAnimation(fadeIn)
        }, 2000)

        Handler().postDelayed({
            zoomStar1.startAnimation(fadeOut)
            zoomStar2.startAnimation(fadeOut)
        }, 3000)


        // Use a Handler to start the second animation after a delay
        Handler().postDelayed({
            val ladySushiLeftEye = findViewById<ImageView>(R.id.left_eye_lady_sushi)
            ladySushiLeftEye.startAnimation(rotateLeftEye)

            val ladySushiRightEye = findViewById<ImageView>(R.id.right_eye_lady_sushi)
            ladySushiRightEye.startAnimation(rotateRightEye)
        }, 1500)
    }
}
