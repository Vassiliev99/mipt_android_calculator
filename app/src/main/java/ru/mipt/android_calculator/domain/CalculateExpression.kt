package ru.mipt.android_calculator.domain

import android.util.Log
import com.fathzer.soft.javaluator.DoubleEvaluator
import java.lang.Exception

fun calculateExpression(expression: String, precision: Int): String {
    return try {
        val resultDouble = DoubleEvaluator().evaluate(expression)
        if (kotlin.math.floor(resultDouble) == resultDouble) {
            resultDouble.toLong().toString()
        } else {
            "%.${precision}f".format(resultDouble)
        }
    } catch (e: Exception) {
        ""
    }
}