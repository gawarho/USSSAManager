package org.mfp.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gawarho on 2/18/18.
 */
public class DateUtil {
    private static String [] textMonths = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
    private static Map<String, Long> textToNumberMap;
    static {
        textToNumberMap = new HashMap<>();
        Long indx = 1L;
        for(String s : textMonths) {
            textToNumberMap.put(s, indx++);
        }

    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    public static String getNumberDateStr(String val) {
        String [] parts = val.split("\\s");
        if(parts.length != 3) {
            System.err.println("Ouch: bad date: " + val);
            return "";
        }

        Long month = textToNumberMap.get(parts[0]);
        if(month == null) {
            System.err.println("Ouch: bad date: " + val);
            return "";
        }

        parts[1] = parts[1].replace(",", "");
        Long day = Long.parseLong(parts[1]);

        Long year = Long.parseLong(parts[2]);

        return String.format("%02d", month) + "/" + String.format("%02d", day) + "/" + String.format("%04d", year);
    }


    public static Date getNumberDate(String val) {
        String tmpStr = getNumberDateStr(val);
        if(tmpStr.length() > 5) {
            try {
                return sdf.parse(tmpStr);
            } catch (ParseException e) {
                e.printStackTrace();
                return new Date();
            }
        } else {
            return new Date();
        }
    }
}
