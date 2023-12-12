package com.example.cardnumbervalidation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextCardNumber: EditText = findViewById(R.id.editTextCardNumber)
        val buttonValidate: Button = findViewById(R.id.buttonValidate)

        buttonValidate.setOnClickListener {
            val cardNumber = editTextCardNumber.text.toString().trim()

            if (cardNumber.isNotEmpty()) {
                val isValid = validateCardNumber(cardNumber)
                showValidationResult(isValid)
            } else {
                val rootView = findViewById<View>(android.R.id.content)
                Snackbar.make(rootView, "Enter your card number.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateCardNumber(number: String): Boolean {
        val numbers = number.filter { it.isDigit() }.reversed()
        var sum = 0
        val parity = numbers.length % 2

        for ((index, digitChar) in numbers.withIndex()) {
            var digit = digitChar.digitToInt()
            if ((index + 1) % 2 == parity) {
                digit *= 2
                if (digit > 9) digit -= 9
            }
            sum += digit
        }

        return sum % 10 == 0
    }

    private fun showValidationResult(isValid: Boolean) {
        val rootView = findViewById<View>(android.R.id.content)
        val message = if (isValid) "The card number is correct." else "The card number is incorrect."
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }
}
