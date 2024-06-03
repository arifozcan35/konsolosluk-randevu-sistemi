package com.arifozcan.consulateapplication.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arifozcan.consulateapplication.model.Place
import com.google.firebase.firestore.auth.User

@Database(entities = [Place::class], version = 1)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}