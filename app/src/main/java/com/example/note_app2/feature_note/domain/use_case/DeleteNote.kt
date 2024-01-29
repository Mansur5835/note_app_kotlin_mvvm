package com.example.note_app2.feature_note.domain.use_case

import com.example.note_app2.feature_note.domain.model.Note
import com.example.note_app2.feature_note.domain.repository.NoteRepository

class DeleteNote(private val repository: NoteRepository) {


    suspend operator fun invoke(note: Note) {
        return repository.deleteNote(note);
    }


}