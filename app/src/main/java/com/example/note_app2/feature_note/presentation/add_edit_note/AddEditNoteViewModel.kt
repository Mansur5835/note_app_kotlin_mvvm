package com.example.note_app2.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter title..."
        )
    )

    val noteTitle: State<NoteTextFieldState> = _noteTitle;


    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter some content"
        )
    )
    val noteContent: State<NoteTextFieldState> = _noteContent;


    private val _noteColor = mutableIntStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor;


    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private var currentNoteId: Int? = null


    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->

            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )

                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )

                        _noteColor.intValue = note.color

                    }
                }

            }
        }
    }


    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.intValue = event.color
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.SaveNote -> {

                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = _noteTitle.value.text,
                                content = _noteContent.value.text,
                                time = System.currentTimeMillis(),
                                color = _noteColor.intValue,
                                id = currentNoteId,

                                )
                        )

                        _eventFlow.emit(UIEvent.SaveNote)

                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                message = e.message ?: ""
                            )
                        )

                    }
                }

            }
        }
    }


    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
        object SaveNote : UIEvent()
    }
}

