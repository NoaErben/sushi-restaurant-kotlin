package com.example.sushirestaurant

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import android.widget.EditText
import android.widget.Button


class ReservationDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_reservation, null)

        val numPeopleSpinner: Spinner = view.findViewById(R.id.num_people_spinner)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.num_people_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            numPeopleSpinner.adapter = adapter
        }

        val hourSpinner: Spinner = view.findViewById(R.id.hours_spinner)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.hours_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            hourSpinner.adapter = adapter
        }

        val datePicker = view.findViewById<DatePicker>(R.id.date_picker)

        val alertDialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle("Reservation")
            .setView(view)

// Retrieve data from views and show the dialog when the submit button is clicked
        view.findViewById<Button>(R.id.submit_button)?.setOnClickListener {
            // Retrieve data from views
            val fullName = view.findViewById<EditText>(R.id.full_name).text.toString()
            val phoneNumber = view.findViewById<EditText>(R.id.phone_number).text.toString()
            val email = view.findViewById<EditText>(R.id.email).text.toString()
            val numPeople = numPeopleSpinner.selectedItem.toString()
            val selectedHour = hourSpinner.selectedItem.toString()
            val dayOfMonth = datePicker.dayOfMonth
            val month = datePicker.month
            val year = datePicker.year

            // Display reservation details in an AlertDialog
            val message = "Full Name: $fullName\n" +
                    "Phone Number: $phoneNumber\n" +
                    "Email: $email\n" +
                    "Number of People: $numPeople\n" +
                    "Selected Hour: $selectedHour\n" +
                    "Date: $dayOfMonth/$month/$year"
            AlertDialog.Builder(requireContext())
                .setTitle("Reservation Details")
                .setMessage(message)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                    // Navigate back to the main activity (MainActivity)
                    val intent = Intent(requireContext(), MainActivity2::class.java)
                    startActivity(intent)
                }
                .show()
        }

        // Handle cancel button click
        view.findViewById<Button>(R.id.cancel_button)?.setOnClickListener {
            // Dismiss the dialog when cancel button is clicked
            dialog?.dismiss()
        }


// Create the AlertDialog
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        return alertDialog
    }
}

