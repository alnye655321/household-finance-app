package com.finance.finance.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class DateUtil {
    public static java.sql.Date stringToSqlDate(String dateString) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date date = null;

        try {
            date = sdf.parse(dateString);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return new Date(date.getTime());
    }
}
