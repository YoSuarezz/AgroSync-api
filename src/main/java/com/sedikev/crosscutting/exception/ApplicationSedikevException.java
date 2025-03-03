package com.sedikev.crosscutting.exception;

import com.sedikev.crosscutting.exception.enums.Layer;

public class ApplicationSedikevException extends SedikevException {

    private static final long serialVersionUID = 1L;

    public ApplicationSedikevException(final String userMessage, final String technicalMessage, final Exception rootException) {
        super(userMessage, technicalMessage, rootException, Layer.APPLICATION);
    }

    public static final ApplicationSedikevException create(final String userMessage, final String technicalMessage, final Exception rootException) {
        return new ApplicationSedikevException(userMessage, technicalMessage, rootException);
    }

    public static final ApplicationSedikevException create(final String userMessage) {
        return new ApplicationSedikevException(userMessage, userMessage, new Exception());
    }

    public static final ApplicationSedikevException create(final String userMessage, final String technicalMessage) {
        return new ApplicationSedikevException(userMessage, technicalMessage, new Exception());
    }
}
