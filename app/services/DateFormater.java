package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormater {
    public static String formatDate(String unformatedData){
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
}
