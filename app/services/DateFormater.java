package services;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateFormater {
    public static String formatJsonDate(String unformatedData){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(unformatedData);
            return new SimpleDateFormat("dd/MM/yyyy").format(date);
        } catch (Exception e){
            System.err.print("Incorrect data formating");
            e.printStackTrace();
        }
        return null;
    }

    public static Date stringToDate(String stringDate, String pattern) throws ParseException {
        return  new SimpleDateFormat(pattern).parse(stringDate);
    }

    public static String dateToString(Date date) throws ParseException{
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }
    public static String dateToString(Date date, String pattern) throws ParseException{
        return new SimpleDateFormat(pattern).format(date);
    }

    public static List<Date> divideDateByPeriod(String dateStart, String dateEnd, int periodInDays){
        List<Date> datesBetween = new ArrayList<Date>();
        try {
            Date first  = stringToDate(dateStart, "dd/MM/yyyy");
            Date second = stringToDate(dateEnd, "dd/MM/yyyy");
            Calendar start = new GregorianCalendar();

            start.setTime(first);
            start.setTime(second);

            datesBetween.add(first);
            while (first.before(second)) {
                first = addDays(first, periodInDays);
                datesBetween.add(first);
                System.out.println(first);
            }
            datesBetween.set(datesBetween.size()-1, second);

        } catch(ParseException e){
            e.printStackTrace();
        }
            return datesBetween;
    }
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
    public static Date getCurrentDate(){
        return new Date();
    }
}
