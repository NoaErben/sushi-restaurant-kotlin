package com.example.sushirestaurant

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ReservationDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Reservation")
                .setMessage("Please provide details for your reservation:")
                .setPositiveButton("Confirm") { dialog, id ->
                    // Handle confirmation
                }
                .setNegativeButton("Cancel") { dialog, id ->
                    // Handle cancellation
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
