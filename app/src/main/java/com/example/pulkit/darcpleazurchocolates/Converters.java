package com.example.pulkit.darcpleazurchocolates;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pulkit on 9/28/2017.
 */

public class Converters {

    @TypeConverter
    public static ArrayList<String> fromString(String value){
        Type list = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value,list);
    }
    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}
