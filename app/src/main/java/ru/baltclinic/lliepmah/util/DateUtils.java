package ru.baltclinic.lliepmah.util;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.baltclinic.lliepmah.R;

public class DateUtils {

    private static final String PATTERN_DISPLAY_DATE = "d MMMM yyyy";
    private static final String PATTERN_SERVER_DATE = "dd.MM.yy";
    private static final String PATTERN_MONTH_DATE = "MM.yy";
    private static final String PATTERN_DISPLAY_TIME = "HH:mm";

    private static final SimpleDateFormat DISPLAY_DATE_FORMAT = new SimpleDateFormat(PATTERN_DISPLAY_DATE, new Locale("ru"));
    private static final SimpleDateFormat MONTH_DATE_FORMAT = new SimpleDateFormat(PATTERN_MONTH_DATE, new Locale("ru"));
    private static final SimpleDateFormat DISPLAY_TIME_FORMAT = new SimpleDateFormat(PATTERN_DISPLAY_TIME, new Locale("ru"));
    private static final SimpleDateFormat SERVER_DATE_FORMAT = new SimpleDateFormat(PATTERN_SERVER_DATE, new Locale("ru"));

    public static Date parseDisplayDate(String date) {
        try {
            return DISPLAY_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            return new Date(System.currentTimeMillis());
        }
    }

    public static String formatDisplayDate(long date) {
        return DISPLAY_DATE_FORMAT.format(new Date(date));
    }

    public static String formatMonthDate(long date) {
        return MONTH_DATE_FORMAT.format(new Date(date));
    }

    public static void showDateDialog(Context context, Date date, OnDateChangedListener onDateChangedListener) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        new DatePickerDialog(context, R.style.BaltMed_Dialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                onDateChangedListener.onDateSet(calendar.getTime());
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public static void showTimeDialog(Context context, Date date, OnTimeChangedListener onTimeChangedListener) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        new TimePickerDialog(context, R.style.BaltMed_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                onTimeChangedListener.onTimeSet(calendar.getTime());
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
                .show();

    }

    public static String formatDisplayTime(long time) {
        return DISPLAY_TIME_FORMAT.format(new Date(time));
    }

    public static String convertFromDisplayToServerDate(String dateString) {
        Date date = parseDisplayDate(dateString);
        return SERVER_DATE_FORMAT.format(date);
    }

    public static Date parseDisplayTime(String time) {
        try {
            return DISPLAY_TIME_FORMAT.parse(time);
        } catch (ParseException e) {
            return new Date(System.currentTimeMillis());
        }
    }

    public interface OnDateChangedListener {
        void onDateSet(Date date);
    }

    public interface OnTimeChangedListener {
        void onTimeSet(Date date);
    }

}
