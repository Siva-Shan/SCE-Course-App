package com.example.sce.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    public static String getCurrentDate(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentDate);
    }
}
