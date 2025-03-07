package com.example.midterm_section2.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Post(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    @ServerTimestamp val timestamp: Date? = null // Firestore auto-sets the timestamp
)
