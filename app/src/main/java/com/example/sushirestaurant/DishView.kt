package com.example.sushirestaurant

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import android.graphics.Color

class DishView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val imageView: ImageView
    private val labelText: TextView
    private val descriptionText: TextView

    init {
        inflate(context, R.layout.dish_view, this)
        imageView = findViewById(R.id.image_view)
        labelText = findViewById(R.id.label_text)
        descriptionText = findViewById(R.id.descriptionText)
        attrs?.let { setAttributes(context, it) }
    }

    private fun setAttributes(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DishView)
        val imageRes = typedArray.getResourceId(R.styleable.DishView_imageSrc, 0)
        val labelText = typedArray.getString(R.styleable.DishView_labelText)
        val labelColor = typedArray.getColor(R.styleable.DishView_labelColor, Color.BLACK)
        val descriptionText = typedArray.getString(R.styleable.DishView_descriptionText)
        typedArray.recycle()

        if (imageRes != 0) {
            imageView.setImageResource(imageRes)
        }

        if (labelText != null) {
            this.labelText.text = labelText
        }

        if (descriptionText != null) {
            this.descriptionText.text = descriptionText
        }

        this.labelText.setTextColor(labelColor)
        this.descriptionText.setTextColor(labelColor)
    }

    fun setImageSrc(@DrawableRes imageRes: Int) {
        imageView.setImageResource(imageRes)
    }

    fun setLabelText(text: String) {
        labelText.text = text
    }
}

