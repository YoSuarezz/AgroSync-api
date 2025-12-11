package com.agrosync.crosscutting.helpers;

import java.time.LocalDate;

public final class DateHelper {

    private DateHelper() {
        super();
    }

    public static LocalDate getDefault() {
        return null;
    }

    public static LocalDate getDefault(final LocalDate value, final LocalDate defaultValue) {
        return (value != null) ? value : defaultValue;
    }

    public static LocalDate getDefaultToday() {
        return LocalDate.now();
    }
}