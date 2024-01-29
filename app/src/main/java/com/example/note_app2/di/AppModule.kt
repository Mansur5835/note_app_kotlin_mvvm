package com.example.note_app2.di

import android.app.Application
import androidx.room.Room
import com.example.note_app2.feature_note.data.data_source.NoteDB
import com.example.note_app2.feature_note.data.repository.NoteRepositoryImpl
import com.example.note_app2.feature_note.domain.repository.NoteRepository
import com.example.note_app2.feature_note.domain.use_case.AddNote
import com.example.note_app2.feature_note.domain.use_case.DeleteNote
import com.example.note_app2.feature_note.domain.use_case.GetNote
import com.example.note_app2.feature_note.domain.use_case.GetNotes
import com.example.note_app2.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNotDatabase(app: Application): NoteDB {
        return Room.databaseBuilder(
            app,
            NoteDB::class.java,
            NoteDB.DATABASE_NAME,
        ).build()
    }


    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDB): NoteRepository {
        return NoteRepositoryImpl(db.noteDao);
    }

    fun provideNoteUseCase(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        );
    }

}