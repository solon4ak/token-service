package ru.tokens.site.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public final class TimeUtils {

    public static String intervalToString(long timeInterval) {
        if (timeInterval < 1_000) {
            return "less than one second";
        }
        if (timeInterval < 60_000) {
            return (timeInterval / 1_000) + " seconds";
        }
        return "about " + (timeInterval / 60_000) + " minutes";
    }
    
    public static int getYearsPassedFromDate(LocalDate birthDate) {
        int age;
        LocalDate now = LocalDate.now();
        age = Period.between(birthDate, now).getYears();        
        return age;
    }
    
    public static LocalDate getDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
