package com.automation.main.validator.ui;

import utils.ATUManager;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lenovo on 29/05/2017.
 */
public class TimeValidator {

    public static void validateTextTime(String time){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try {
            long yesterdayInMilli = format.parse(time).getTime();
            long todayInMillis = format.parse(cal.toString()).getTime();
            boolean isYesterdayDate = todayInMillis>yesterdayInMilli;
            ATUManager.asserIsTrueAndReport(isYesterdayDate,"The date expected to be yesterday");


        } catch (ParseException e) {
            ATUManager.asserIsTrueAndReport(false,"Couldn;t pars date format "+e);
        }
        System.out.println(date);
    }
}
