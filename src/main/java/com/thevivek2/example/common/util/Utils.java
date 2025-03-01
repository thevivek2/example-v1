package com.thevivek2.example.common.util;

public class Utils {
    public static String fullName(final String firstName, final String middleName, final String lastName) {
        return firstName.trim()
                + ((null != middleName && !middleName.trim().isEmpty())
                ? " " + middleName.trim() : "")
                + ((null != lastName && !lastName.trim().isEmpty())
                ? " " + lastName.trim() : "");
    }
}
