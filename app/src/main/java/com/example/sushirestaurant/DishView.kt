package com.example.sushirestaurant

import android.app.Dialog
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import android.graphics.Color

class DishView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private val imageView: ImageView
    private val labelText: TextView
    private val descriptionText: TextView
    private val price : TextView
    private var imageRes: Int = 0
    private var description: String = ""

    init {
        LayoutInflater.from(context).inflate(R.layout.dish_view, this, true)
        imageView = findViewById(R.id.image_view)
        labelText = findViewById(R.id.label_text)
        descriptionText = findViewById(R.id.descriptionText)
        price = findViewById(R.id.price)
        attrs?.let { setAttributes(context, it) }

        // Set click listener
        setOnClickListener(this)
    }

    private fun setAttributes(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DishView)
        imageRes = typedArray.getResourceId(R.styleable.DishView_imageSrc, 0)
        val labelText = typedArray.getString(R.styleable.DishView_labelText)
        val labelColor = typedArray.getColor(R.styleable.DishView_labelColor, Color.BLACK)
        description = typedArray.getString(R.styleable.DishView_descriptionText) ?: ""
        val priceText = typedArray.getString(R.styleable.DishView_price)
        val priceColor = typedArray.getColor(R.styleable.DishView_priceColor, ContextCompat.getColor(context, R.color.purple))
        typedArray.recycle()

        if (imageRes != 0) {
            imageView.setImageResource(imageRes)
        }

        if (labelText != null) {
            this.labelText.text = labelText
        }

        if (priceText != null) {
            this.price.text = priceText
        }

        this.labelText.setTextColor(labelColor)
        this.descriptionText.setTextColor(labelColor)
        this.price.setTextColor(priceColor)
    }

    fun setImageSrc(@DrawableRes imageRes: Int) {
        this.imageRes = imageRes
        imageView.setImageResource(imageRes)
    }

    fun setLabelText(text: String) {
        labelText.text = text
    }

    override fun onClick(v: View?) {
        showDishDialog()
    }

    private fun showDishDialog() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        // Set the dialog content view to the layout containing both title and dish content
        dialog.setContentView(R.layout.dialog_dish)

        // Find title TextView and set its text
        val titleTextView: TextView = dialog.findViewById(R.id.dialog_title)
        titleTextView.text = labelText.text // Set your desired title here

        val priceView : TextView = dialog.findViewById(R.id.dialog_price)
        priceView.text = price.text

        // Find dish ImageView and set its image resource
        val dialogImageView: ImageView = dialog.findViewById(R.id.dialog_image)
        dialogImageView.setImageResource(imageRes)

        // Find description TextView and set its text
        val descriptionTextView: TextView = dialog.findViewById(R.id.dialog_description)
        descriptionTextView.text = description

        dialog.show()
    }
}
