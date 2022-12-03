package com.jig.UdemyReader.utils

import android.annotation.SuppressLint
import android.icu.text.DateFormat
import com.google.firebase.Timestamp


@SuppressLint("NewApi")
fun formatDate(timestamp: Timestamp): String {
    return DateFormat.getDateInstance().format(timestamp.toDate()).toString().split(",")[0]
}