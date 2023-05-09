package com.example.driver.Helper;

import java.sql.Date;

public class CardHelper {
    public static int compareDate(Date date1, Date date2) {
        if(date1.equals(date2)){
            return 0;
        }
        else if(date1.after(date2)){
            return 1;
        }
        else{
            return -1;
        }
    }
    public static Date addExpiry(Integer years){
        Date date=new Date(System.currentTimeMillis());
        return Date.valueOf(date.toLocalDate().plusYears(years));
    }
}
