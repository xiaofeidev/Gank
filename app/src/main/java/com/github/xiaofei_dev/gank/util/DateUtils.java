package com.github.xiaofei_dev.gank.util;

import com.github.xiaofei_dev.gank.ui.fragment.GankDayFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/1.
 */

public final class DateUtils {

    //public static  Date date = new Date(System.currentTimeMillis());
    public static Date getCurrentDate(){
        return new Date(System.currentTimeMillis());
    }

    public static String toDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }


    public static String toWorkDate(Date date, GankDayFragment gankDayFragment) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, gankDayFragment.getCount());

        if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
            gankDayFragment.setCount();
            return  toWorkDate(date, gankDayFragment);
        }else if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
            gankDayFragment.setCount();
            gankDayFragment.setCount();
            return  toWorkDate(date, gankDayFragment);
        }
        gankDayFragment.setCount();
        return toDate(calendar.getTime());
//        if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
//            gankDayFragment.setCount();
//            calendar.add(Calendar.DATE, gankDayFragment.getCount());
//            gankDayFragment.setCount();
//            return toDate(calendar.getTime());
//        }else if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
//            gankDayFragment.setCount();
//            gankDayFragment.setCount();
//            calendar.add(Calendar.DATE, gankDayFragment.getCount());
//            gankDayFragment.setCount();
//            return toDate(calendar.getTime());
//        }else {
//            gankDayFragment.setCount();
//            return toDate(calendar.getTime());
//        }
    }

//    public static String toWorkDate(Date date, int count) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DATE, count);
//
//        if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
//            return toWorkDate(date,count - 1);
//        }else (calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
//            return toWorkDate(date,count - 2);
//        }
//
//        return toDate(calendar.getTime());
//
//    }

    public static String dateFormat(String timestamp) {
        if (timestamp == null) {
            return "unknown";
        }
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = inputFormat.parse(timestamp);
            return outputFormat.format(date);
        } catch (ParseException e) {
            return "unknown";
        }
    }

//    public static Date getLastdayDate(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DATE, -1);
//        return calendar.getTime();
//    }
//
//
//    public static Date getNextdayDate(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DATE, 1);
//        return calendar.getTime();
//    }
//
//
//    public static boolean isTheSameDay(Date one, Date another) {
//        Calendar _one = Calendar.getInstance();
//        _one.setTime(one);
//        Calendar _another = Calendar.getInstance();
//        _another.setTime(another);
//        int oneDay = _one.get(Calendar.DAY_OF_YEAR);
//        int anotherDay = _another.get(Calendar.DAY_OF_YEAR);
//
//        return oneDay == anotherDay;
//    }
}
