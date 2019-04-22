package ru.tokens.site.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author solon4ak
 */
public class MessageEventHelper {

    private static final Map<String, Integer> intervals = new LinkedHashMap<>();

    static {
        intervals.put("Week", 7);
        intervals.put("Month", 30);
        intervals.put("3 Months", 91);
        intervals.put("6 Months", 183);
        intervals.put("Year", 365);
    }

    public static Map<String, Integer> getIntervals() {
        return intervals;
    }

}
