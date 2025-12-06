package com.agrosync.crosscutting.helpers;

import java.time.LocalDate;

public final class DateHelper {

    private DateHelper() {
        super();
    }

    public static final LocalDate DEFAULT_DATE = LocalDate.MIN;

    public static LocalDate getDefault() {
        return DEFAULT_DATE;
    }

    public static LocalDate getDefault(final LocalDate value, final LocalDate defaultValue) {
        return (value != null) ? value : defaultValue;
    }
}