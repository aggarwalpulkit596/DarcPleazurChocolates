package com.example.pulkit.darcpleazurchocolates;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.pulkit.darcpleazurchocolates.Models.Chocolates;

import java.util.List;

/**
 * Created by Pulkit on 9/26/2017.
 */
@Dao
public interface ChocolateDao {

    @Query("SELECT * FROM chocolates")
    List<Chocolates> getAllChocolates();

    @Insert
    void addtocart(Chocolates chocolates);

    @Delete
    void deletefromcart(Chocolates chocolates);


}
