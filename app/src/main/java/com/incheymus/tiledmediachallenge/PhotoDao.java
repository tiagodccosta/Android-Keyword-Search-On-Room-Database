package com.incheymus.tiledmediachallenge;

import java.util.List;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PhotoDao {

    @Insert
    void insert(Photo photo);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Photo> photos);

    @Query("SELECT * FROM photos WHERE name LIKE '%' || :keyword || '%'")
    List<Photo> searchPhotosByKeyword(String keyword);

    @Query("SELECT * FROM photos")
    List<Photo> getAllPhotos();

    @Query("SELECT COUNT(*) FROM photos")
    int getPhotoCount();
}
