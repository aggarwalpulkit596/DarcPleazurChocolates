package com.example.pulkit.darcpleazurchocolates;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.pulkit.darcpleazurchocolates.Models.Chocolates;

/**
 * Created by Pulkit on 9/26/2017.
 */

@Database(entities = {Chocolates.class},version = 2)
@TypeConverters({Converters.class})
public abstract class ChocolateDatabse extends RoomDatabase {

    private static ChocolateDatabse INSTANCE;

    private static Object LOCK = new Object();

    public static ChocolateDatabse getInstance(Context context){
        if(INSTANCE == null){
            synchronized (LOCK){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                            ,ChocolateDatabse.class,ChocolateDatabse.DB_NAME).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final String DB_NAME = "chocolates_db";

    abstract ChocolateDao chocolateDao();
}
