/**
 * This class represents a dialog fragment for making restaurant reservations.
 * It allows users to input their reservation details such as name, phone number, email, number of people,
 * selected hour, date, dietary preferences, and payment method. It also provides validation for required fields
 * and displays reservation details in an alert dialog before navigating back to the main activity.
 */

// widget 1 - DatePicker with OnDateChangedListener
// widget 2 - Switch with OnCheckedChangeListener
// widget 3 - SeekBar with setOnSeekBarChangeListener
// widget 4 - checkBox with setOnCheckedChangeListener

// widget 4 - EditText ? or checkBox

package com.example.sushirestaurant

import android.app.Dialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import android.widget.EditText
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat

class ReservationDialogFragment : DialogFragment(), DatePicker.OnDateChangedListener {

    private var selectedDate: String? = null
    private var handler: Handler = Handler()
    private var dateChangedRunnable: Runnable = Runnable { }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_reservation, null)

        // Initialize subscribeSwitch and set its OnCheckedChangeListener
        val subscribeSwitch: SwitchCompat = view.findViewById(R.id.subscribe_switch)
        subscribeSwitch.setOnCheckedChangeListener { _, isChecked ->
            val toastMessage = if (isChecked) getString(R.string.subscribe_yes) else getString(R.string.subscribe_no)
            Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
        }


        // Initialize spinner for selecting the hour
        val hourSpinner: Spinner = view.findViewById(R.id.hours_spinner)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.hours_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            hourSpinner.adapter = adapter
        }

        // Initialize DatePicker and set listener
        val datePicker = view.findViewById<DatePicker>(R.id.date_picker)
        datePicker.init(datePicker.year, datePicker.month, datePicker.dayOfMonth, this)

        // Initialize SeekBar and TextView for selecting the number of people
        val numPeopleSeekBar= view.findViewById<SeekBar>(R.id.num_people_seek_bar)
        val numPeopleTextView: TextView = view.findViewById(R.id.num_people_text_view)

        numPeopleSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                numPeopleTextView.text = (progress+1).toString() // Add 1 to progress since SeekBar starts from 0
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Build AlertDialog
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.reservation_title))
            .setView(view)
        val alertDialog = alertDialogBuilder.create()

        // Handle allergy CheckBox click
        val allergyCheckbox = view.findViewById<CheckBox>(R.id.allergy_checkbox)
        val allergyDescription = view.findViewById<EditText>(R.id.allergy_description)

        allergyCheckbox.setOnCheckedChangeListener { _, isChecked ->
            // Show or hide the allergy description EditText based on checkbox state
            allergyDescription.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        val nameEditText = view.findViewById<EditText>(R.id.full_name)
        val nameErrorTextView = view.findViewById<TextView>(R.id.full_name_error)

        // Set onFocusChangeListener to full name EditText
        nameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // Validate name when focus leaves the EditText
                val name = nameEditText.text.toString()

                // Validate name
                val validName = validateName(name)

                if (!validName) {
                    // Display error message if name is invalid
                    nameErrorTextView.visibility = View.VISIBLE
                } else {
                    // Hide error message if name is valid
                    nameErrorTextView.visibility = View.GONE
                }
            }
        }

        // Find the EditText and TextView for phone number
        val phoneNumberEditText = view.findViewById<EditText>(R.id.phone_number)
        val phoneNumberErrorTextView = view.findViewById<TextView>(R.id.phone_number_error)

        // Set onFocusChangeListener to phone number EditText
        phoneNumberEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // Validate phone number when focus leaves the EditText
                val phoneNumber = phoneNumberEditText.text.toString()

                // Validate phone number
                val validPhoneNumber = validatePhoneNumber(phoneNumber)

                if (!validPhoneNumber) {
                    // Display error message if phone number is invalid
                    phoneNumberErrorTextView.visibility = View.VISIBLE
                } else {
                    // Hide error message if phone number is valid
                    phoneNumberErrorTextView.visibility = View.GONE
                }
            }
        }


        val mailEditText = view.findViewById<EditText>(R.id.email)
        val mailErrorTextView = view.findViewById<TextView>(R.id.mail_error)

        // Set onFocusChangeListener to mail EditText
        mailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // Validate mail when focus leaves the EditText
                val mail = mailEditText.text.toString()

                // Validate mail
                val validMail = validateMail(mail)

                if (!validMail) {
                    // Display error message if mail is invalid
                    mailErrorTextView.visibility = View.VISIBLE
                } else {
                    // Hide error message if mail is valid
                    mailErrorTextView.visibility = View.GONE
                }
            }
        }




        // Handle submit button click
        view.findViewById<Button>(R.id.submit_button)?.setOnClickListener {
            // Retrieve data from views
            val fullName = nameEditText.text.toString()
            val phoneNumber = phoneNumberEditText.text.toString()
            val email = mailEditText.text.toString()
            val numPeople = if (numPeopleSeekBar.progress == 0) "1" else numPeopleSeekBar.progress?.toString() ?: "1" // Default to "1" if no number of people is selected
            val selectedHour = hourSpinner.selectedItem?.toString() ?: "12:00" // Default to "12:00" if no hour is selected
            // Set month to be " month+1 because the indexing for months starts from 0"
            val selectedDate = datePicker?.run {
                String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            } ?: run {
                val currentDate = Calendar.getInstance()
                String.format("%02d/%02d/%d", currentDate.get(Calendar.DAY_OF_MONTH), currentDate.get(Calendar.MONTH) + 1, currentDate.get(Calendar.YEAR))
            } // Default to today's date if no date is selected
            val veganYes = view.findViewById<RadioButton>(R.id.vegan_yes_answer).isChecked
            val veganNo = view.findViewById<RadioButton>(R.id.vegan_no_answer).isChecked
            val paymentCash = view.findViewById<RadioButton>(R.id.cash).isChecked
            val creditCard = view.findViewById<RadioButton>(R.id.credit_card).isChecked
            val buyMe = view.findViewById<RadioButton>(R.id.buyme).isChecked
            val allergyDescriptionText = allergyDescription.text.toString()

            // Save the user's answers
            val veganAnswer = if (veganYes) getString(R.string.yes) else getString(R.string.no)
            val paymentMethodAnswer = when {
                paymentCash -> getString(R.string.cash)
                creditCard -> getString(R.string.credit_card)
                buyMe -> getString(R.string.buyme)
                else -> getString(R.string.none)
            }

            // Save the labels of the reservation details
            val fullNameLabel = getString(R.string.full_name_label)
            val phoneNumberLabel = getString(R.string.phone_number_label)
            val emailLabel = getString(R.string.email_label)
            val numPeopleLabel = getString(R.string.num_people_label)
            val selectedHourLabel = getString(R.string.selected_hour_label)
            val dateLabel = getString(R.string.date_label)
            val veganLabel = getString(R.string.vegan_label)
            val paymentLabel = getString(R.string.payment_label)
            val allergyLabel = getString(R.string.allergies_label)

            val okButtonLabel = getString(R.string.ok_button_label)

            if (fullName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || (!veganYes && !veganNo)
                || (!paymentCash && !creditCard && !buyMe || !validateMail(mailEditText.text.toString())
                        || !validatePhoneNumber(phoneNumberEditText.text.toString()) || !validateName(nameEditText.text.toString()))) {
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
                        "$dateLabel $selectedDate\n" +
                        "$veganLabel $veganAnswer\n" +
                        "$paymentLabel $paymentMethodAnswer\n" +
                        if (allergyDescriptionText.isNotEmpty()) "$allergyLabel $allergyDescriptionText\n" else ""


                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.reservation_deatils_title))
                    .setMessage(message)
                    .setPositiveButton(okButtonLabel) { dialog, _ ->
                        dialog.dismiss()
                        // Navigate back to MainActivity2
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




    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        // Format the selected date
        selectedDate = String.format("%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year)
        // Remove any existing callbacks to avoid multiple executions
        handler.removeCallbacks(dateChangedRunnable)
        // Post a delayed runnable to update the toast message
        dateChangedRunnable = Runnable {
            val message = getString(R.string.selected_date, selectedDate)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
        handler.postDelayed(dateChangedRunnable, 1000) // Delay the message's show time
    }

    // Function to validate name format
    private fun validateName(name: String): Boolean {
        // Length Check: Minimum 2 characters, maximum 50 characters
        val lengthRegex = "^.{2,50}$".toRegex()

        // Character Set Check: Letters, spaces, hyphens, and apostrophes
        val characterSetRegex = "^[a-zA-Z\\s'-]+$".toRegex()

        // Regular Expression Check: Alphabetical characters with optional spaces and certain special characters
        val regexCheck = "^[a-zA-Z]+(?:[' -][a-zA-Z]+)*$".toRegex()

        // Perform validation using regular expressions
        val isLengthValid = lengthRegex.matches(name)
        val isCharacterSetValid = characterSetRegex.matches(name)
        val isRegexValid = regexCheck.matches(name)

        // Return true if all conditions are met
        return isLengthValid && isCharacterSetValid && isRegexValid
    }


    // Function to validate phone number format
    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        val pattern = "\\d{10}".toRegex() // Regular expression to match 10 digits
        return pattern.matches(phoneNumber)
    }

    // Function to validate email format
    private fun validateMail(mail: String): Boolean {
        val pattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()
        return pattern.matches(mail)
    }

}
