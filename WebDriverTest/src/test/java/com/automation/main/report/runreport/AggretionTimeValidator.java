package com.automation.main.report.runreport;

import com.automation.main.report.page.ReportResultSection;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Lenovo on 20/06/2017.
 */
public class AggretionTimeValidator {


    String[] monthNames = {"January", "February",
            "March", "April", "May", "June", "July",
            "August", "September", "October", "November",
            "December"};


    public String validateAggregationTimeByType(ReportResultSection.AggregationTime aggregationTime) {
        switch (aggregationTime) {
            case Year:
                return "Year " + getYearNumber();
            case Month:
                int numberMonth = Calendar.getInstance().get(Calendar.MONTH);
                return monthNames[numberMonth];
            case Week:
                return getWeekRange();
        }
        return null;
    }

    private String getWeekRange() {
        Calendar current = Calendar.getInstance();
        int theNumberOfCurrentDay = new GregorianCalendar().get(Calendar.DAY_OF_WEEK);
        current.add(Calendar.DATE, 7 - theNumberOfCurrentDay);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        String toDate = sdf.format(current.getTime());
        current.add(Calendar.DATE, -6);
        String fromDate = sdf.format(current.getTime());
        return fromDate + " - " + toDate;
    }

    private int getYearNumber() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

}
