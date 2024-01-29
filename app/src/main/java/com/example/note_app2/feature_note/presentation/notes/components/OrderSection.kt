package com.example.note_app2.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.note_app2.feature_note.domain.util.NoteOrder
import com.example.note_app2.feature_note.domain.util.OrderType


@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(title = "Title", checked = noteOrder is NoteOrder.Title, onSelect = {
                onOrderChange(NoteOrder.Title(noteOrder.orderType))
            })
            Spacer(modifier = Modifier.width(10.dp))
            DefaultRadioButton(title = "Date", checked = noteOrder is NoteOrder.Date, onSelect = {
                onOrderChange(NoteOrder.Date(noteOrder.orderType))
            })
            Spacer(modifier = Modifier.width(10.dp))
            DefaultRadioButton(title = "Color", checked = noteOrder is NoteOrder.Color, onSelect = {
                onOrderChange(NoteOrder.Color(noteOrder.orderType))
            })

        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                title = "Ascending",
                checked = noteOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Ascending))
                })
            Spacer(modifier = Modifier.width(10.dp))
            DefaultRadioButton(
                title = "Descending",
                checked = noteOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(noteOrder.copy(OrderType.Descending))
                })


        }
    }
}