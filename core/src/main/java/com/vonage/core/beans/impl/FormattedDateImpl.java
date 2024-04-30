package com.vonage.core.beans.impl;

import com.vonage.core.beans.FormattedDate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Helper class to help identify Dates.
 *
 * @author Vonage
 *
 */
public final class FormattedDateImpl implements FormattedDate {

    /**
     * current year field
     */
    private String systemYear;

    /**
     * @param yearFormat  formattedYear format
     */
    public FormattedDateImpl(final String yearFormat) {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern(yearFormat);
        this.systemYear = localDate.format(yearFormatter);
    }

    @Override
    public String getSystemYear() {
        return this.systemYear;
    }

}
