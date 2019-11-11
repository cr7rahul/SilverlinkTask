package com.solutionsmax.silverlinktask.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FactsDAO {
    @Insert
    void insert(Facts facts);

    @Query("select * from facts")
    LiveData<List<Facts>> getAllfacts();

    @Query("delete from facts")
    void deleteAll();
}
