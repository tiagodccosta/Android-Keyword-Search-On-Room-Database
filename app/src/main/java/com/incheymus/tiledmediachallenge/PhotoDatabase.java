package com.incheymus.tiledmediachallenge;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Photo.class}, version = 1, exportSchema = false)
public abstract class PhotoDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "photos-database";
    private static volatile PhotoDatabase instance;

    public abstract PhotoDao photoDao();

    public static synchronized PhotoDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
            PhotoDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
