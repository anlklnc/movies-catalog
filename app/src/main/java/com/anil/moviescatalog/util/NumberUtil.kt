package com.anil.moviescatalog.util

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

object NumberUtil {
    fun round(number: Double): Double {
        val df = DecimalFormat("#.#")
        df.decimalFormatSymbols = DecimalFormatSymbols().also { it.decimalSeparator = '.' }
        df.roundingMode = RoundingMode.HALF_EVEN
        return df.format(number).toDouble()
    }
}