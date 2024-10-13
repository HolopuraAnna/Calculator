package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var displayTextView: TextView // To show the current input and result
    private var currentNumber: String = "" // Holds the current number being input
    private var operator: String = "" // Holds the selected operator (+, -, *, /)
    private var firstNumber: Double = 0.0 // The first operand
    private var secondNumber: Double = 0.0 // The second operand
    private var equation: String = "" // Equation to show on the display
    private var isOperatorPressed: Boolean = false // True if an operator has been pressed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayTextView = findViewById(R.id.textView)

        val buttonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnAdd, R.id.btnMinus, R.id.btnMultiplication, R.id.btnDevide,
            R.id.btnDot, R.id.btnClean, R.id.btnDelete, R.id.btnEqual)

        fun performOperation(): Double {
            return when (operator) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "*" -> firstNumber * secondNumber
                "/" -> if (secondNumber != 0.0) firstNumber / secondNumber else Double.NaN
                else -> 0.0
            }
        }

        fun onButtonClick(view: View) {
            val button = view as Button

            when (button.id) {
                R.id.btnClean -> {
                    currentNumber = ""
                    operator = ""
                    firstNumber = 0.0
                    secondNumber = 0.0
                    equation = ""
                    isOperatorPressed = false
                    displayTextView.text = "0"
                }
                R.id.btnDelete -> {
                    if (currentNumber.isNotEmpty()) {
                        currentNumber = currentNumber.dropLast(1)
                        equation = equation.dropLast(1)
                        displayTextView.text = equation.ifEmpty {"0"}
                    }
                }
                R.id.btnEqual -> {
                    if (isOperatorPressed) {
                        secondNumber = currentNumber.toDouble()
                        val result = performOperation()
                        displayTextView.text = "$result"
                        currentNumber = result.toString()
                        isOperatorPressed = false
                        equation = ""
                    }
                }
                R.id.btnAdd, R.id.btnMinus, R.id.btnMultiplication, R.id.btnDevide -> {
                    if (currentNumber.isNotEmpty()) {
                        firstNumber = currentNumber.toDouble()
                        operator = button.text.toString()
                        isOperatorPressed = true
                        currentNumber = ""
                        equation += button.text
                        displayTextView.text = equation
                    }
                }
                else -> {
                    if (button.text == "." && currentNumber.contains(".")) return // Prevent multiple dots
                    currentNumber += button.text
                    equation += button.text
                    displayTextView.text = equation                }
            }
        }

        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener { onButtonClick(it) }
        }
    }
}