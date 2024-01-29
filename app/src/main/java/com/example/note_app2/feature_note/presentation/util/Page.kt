package com.example.note_app2.feature_note.presentation.util

sealed class Page(val route: String){
    object NotesPage: Page("notes_page")
    object AddEditNotePage: Page("add_edit_note_page")
}
