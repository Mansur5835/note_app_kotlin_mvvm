package com.example.note_app2.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val title: String,
    val content: String,
    val time: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(Color.Red, Color.Blue, Color.Yellow)
    }
}


class InvalidNoteException(message: String) : Exception(message);



