package com.wadektech.keeper.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wadektech.keeper.models.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteRoomDatabase : RoomDatabase(){
     abstract fun noteDao(): NoteDao

    companion object{
        @Volatile
        private var roomInstance : NoteRoomDatabase  ?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = roomInstance ?: synchronized(LOCK){
            roomInstance ?: createRoomDatabase(context).also { roomInstance = it }
        }

        private fun createRoomDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NoteRoomDatabase::class.java,
            "NOTE_ROOM_DB"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}