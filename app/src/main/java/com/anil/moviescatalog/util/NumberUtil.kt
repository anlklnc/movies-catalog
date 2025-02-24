package com.anil.moviescatalog.util

import java.math.RoundingMode
import java.text.DecimalFormat

object NumberUtil {
    fun round(number: Double): Double {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.HALF_EVEN
        return df.format(number).toDouble()
    }
}