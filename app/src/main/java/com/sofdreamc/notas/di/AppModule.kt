package com.sofdreamc.notas.di

import android.content.Context
import androidx.room.Room
import com.sofdreamc.notas.data.local.db.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        NoteDatabase::class.java,
        "note_db.db"
    ).build()

    @Singleton
    @Provides
    fun provideNoteDao(db: NoteDatabase) = db.getNoteDao()
}