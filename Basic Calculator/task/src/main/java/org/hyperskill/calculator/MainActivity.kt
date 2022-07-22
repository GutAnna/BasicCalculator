package org.hyperskill.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged

object Calculator {
    var operator = ""
    var operand1 = "0"
    var operand2 = "0"

    fun calc(): String {
        val op1 = operand1.toDouble()
        val op2 = operand2.toDouble()
        val result = when (operator) {
            "+" -> op1 + op2
            "-" -> op1 - op2
            "*" -> op1 * op2
            "/" -> op1 / op2
            else -> null
        }
        return if (result!! % 1 == 0.0) result.toInt().toString() else
        result.toString()
    }

    fun init(_operator: String, edit: EditText) {
        operator = _operator
        operand1 = edit.text.toString()
        edit.hint = edit.text
        edit.setText("")
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edit = findViewById<EditText>(R.id.editText)
        edit.doOnTextChanged { text, start, before, count ->
            if (text == "00") edit.setText("0")
        }

        val numButtonList = mutableListOf<Button>()
        numButtonList.add(findViewById(R.id.button0))
        numButtonList.add(findViewById(R.id.button1))
        numButtonList.add(findViewById(R.id.button2))
        numButtonList.add(findViewById(R.id.button3))
        numButtonList.add(findViewById(R.id.button4))
        numButtonList.add(findViewById(R.id.button5))
        numButtonList.add(findViewById(R.id.button6))
        numButtonList.add(findViewById(R.id.button7))
        numButtonList.add(findViewById(R.id.button8))
        numButtonList.add(findViewById(R.id.button9))

        for (button in numButtonList) {
            button.setOnClickListener {
                when {
                    edit.text.toString() == "0" -> edit.setText(button.text)
                    edit.text.toString() == "-0" -> { edit.setText("-"); edit.append(button.text)}
                    else -> edit.text.append(button.text)
                }
            }
        }

        findViewById<Button>(R.id.clearButton).setOnClickListener { edit.hint = "0"; edit.setText("") }
        findViewById<Button>(R.id.dotButton).setOnClickListener {
            if (!edit.text.contains('.')) {
                if (edit.text.toString() == "-") edit.text.append('0')
                    edit.text.append('.')
            }
        }

        findViewById<Button>(R.id.subtractButton).setOnClickListener {
            Calculator.operand1 = edit.text.toString()
            if (edit.text.toString() == "0") edit.setText("-") else {
                Calculator.init("-", edit)
            }
        }
        findViewById<Button>(R.id.addButton).setOnClickListener {
            Calculator.init("+", edit)
        }
        findViewById<Button>(R.id.divideButton).setOnClickListener {
            Calculator.init("/", edit)
        }
        findViewById<Button>(R.id.multiplyButton).setOnClickListener {
            Calculator.init("*", edit)
        }
        findViewById<Button>(R.id.equalButton).setOnClickListener {
            Calculator.operand2 = edit.text.toString()
            edit.setText(Calculator.calc())
        }
    }
}