package com.incheymus.tiledmediachallenge;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hashValues")
public class HashValue {
    @PrimaryKey
    private long id;

    private String hashValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHashValue() {
        return hashValue;
    }

    public void setHashValue(String hashValue) {
        this.hashValue = hashValue;
    }
}
