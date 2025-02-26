package com.anil.moviescatalog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RedLineSeperator(redLength: Int = 1) {
    val list = mutableListOf(Color.Transparent, Color.Red, Color.Transparent)
    if(redLength > 1) {
        val reds = List(redLength) { Color.Red }
        list.addAll(1, reds)
    }
    Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(brush = Brush.horizontalGradient(list)))
}