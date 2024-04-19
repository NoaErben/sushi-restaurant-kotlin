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
import android.widget.RadioButton


class ReservationDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_reservation, null)

        //todo - check if it is a widget type

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
            .setTitle(getString(R.string.reservation_title))
            .setView(view)

        // Retrieve data from views and show the dialog when the submit button is clicked
        val alertDialog = alertDialogBuilder.create()

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
            val veganYes = view.findViewById<RadioButton>(R.id.vegan_yes_answer).isChecked
            val veganNo = view.findViewById<RadioButton>(R.id.vegan_no_answer).isChecked
            val paymentCash = view.findViewById<RadioButton>(R.id.cash).isChecked
            val creditCard = view.findViewById<RadioButton>(R.id.credit_card).isChecked
            val buyMe = view.findViewById<RadioButton>(R.id.buyme).isChecked

            // save the user's answers
            val veganAnswer = if (veganYes) getString(R.string.yes) else getString(R.string.no)
            val paymentMethodAnswer = when {
                paymentCash -> getString(R.string.cash)
                creditCard -> getString(R.string.credit_card)
                buyMe -> getString(R.string.buyme)
                else -> getString(R.string.none)
            }

            // save the labels of the reservation details
            val fullNameLabel = getString(R.string.full_name_label)
            val phoneNumberLabel = getString(R.string.phone_number_label)
            val emailLabel = getString(R.string.email_label)
            val numPeopleLabel = getString(R.string.num_people_label)
            val selectedHourLabel = getString(R.string.selected_hour_label)
            val dateLabel = getString(R.string.date_label)
            val veganLabel = getString(R.string.vegan_label)
            val paymentLabel = getString(R.string.payment_label)

            val okButtonLabel =  getString(R.string.ok_button_label)


            if (fullName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || (!veganYes && !veganNo)
                || (!paymentCash && !creditCard && !buyMe)) {
                // Display error message if any required field is empty
                AlertDialog.Builder(requireContext())
                    .setTitle((getString(R.string.error)))
                    .setMessage(getString(R.string.error_message))
                    .setPositiveButton(okButtonLabel, null)
                    .show()
            } else {
                // Proceed with displaying reservation details if all required fields are filled
                val message = "$fullNameLabel $fullName\n" +
                        "$phoneNumberLabel $phoneNumber\n" +
                        "$emailLabel $email\n" +
                        "$numPeopleLabel $numPeople\n" +
                        "$selectedHourLabel $selectedHour\n" +
                        "$dateLabel $dayOfMonth/$month/$year\n" +
                        "$veganLabel $veganAnswer\n" +
                        "$paymentLabel $paymentMethodAnswer\n"
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.reservation_deatils_title))
                    .setMessage(message)
                    .setPositiveButton(okButtonLabel) { dialog, _ ->
                        dialog.dismiss()
                        // Navigate back to the main activity (MainActivity)
                        val intent = Intent(requireContext(), MainActivity2::class.java)
                        startActivity(intent)
                    }
                    .show()

            }
        }

        // Handle cancel button click
        view.findViewById<Button>(R.id.cancel_button)?.setOnClickListener {
            // Dismiss the dialog when cancel button is clicked
            alertDialog.dismiss()
        }

        alertDialog.show()
        return alertDialog
    }
}