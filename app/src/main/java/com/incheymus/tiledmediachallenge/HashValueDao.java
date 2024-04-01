package com.incheymus.tiledmediachallenge;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface HashValueDao {

    @Query("SELECT hashValue FROM hashValues LIMIT 1")
    String getHashValue();

}
