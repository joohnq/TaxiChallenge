package com.joohhq.taxichallenge.utils

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Locale

object DateTimeFormatter {
    operator fun invoke(input: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val formatterInput = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val dateTime = LocalDateTime.parse(input, formatterInput)
            val formatterOutput = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm")
            dateTime.format(formatterOutput)
        } else {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val date = inputFormat.parse(input) ?: throw Exception("Invalid date")
            val outputFormat = SimpleDateFormat("dd/MM/yyyy 'at' HH:mm", Locale.getDefault())
            outputFormat.format(date)
        }
    }
}