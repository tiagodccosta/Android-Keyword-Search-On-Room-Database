package com.incheymus.tiledmediachallenge;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "photos")
public class Photo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String contentUrl;

    public Photo(String name, String contentUrl) {
        this.name = name;
        this.contentUrl = contentUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentUrl() {
        return contentUrl;
    }
}
