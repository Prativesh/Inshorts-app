package com.example.videoapp.database;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    @TypeConverter
    public static String fromGenreIdsList(List<Integer> genreIds) {
        StringBuilder result = new StringBuilder();

        for(int genreId: genreIds) {
            if(result.length() > 0)
                result.append(",");
            result.append(genreId);
        }
        return result.toString();
    }

    @TypeConverter
    public static List<Integer> toGenreIdsList(String genreIds) {
        List<Integer> result = new ArrayList<>();
        if(genreIds != null && !genreIds.isEmpty()) {
            String[] ids = genreIds.split(",");
            for(String id: ids)
                result.add(Integer.parseInt(id));
        }
        return result;
    }
}
