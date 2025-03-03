package com.sedikev.crosscutting.exception;

import com.sedikev.crosscutting.exception.enums.Layer;
import com.sedikev.crosscutting.helpers.ObjectHelper;
import com.sedikev.crosscutting.helpers.TextHelper;

public class SedikevException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String userMessage;
    private final Layer layer;

    public SedikevException(final String userMessage, final String technicalMessage, final Exception rootException, final Layer layer) {
        super(ObjectHelper.getDefault(technicalMessage, TextHelper.applyTrim(userMessage)),
                ObjectHelper.getDefault(rootException, new Exception()));
        this.userMessage = TextHelper.applyTrim(userMessage);
        this.layer = ObjectHelper.getDefault(layer, Layer.GENERAL);
    }

    public String getUserMessage() {
        return userMessage;
    }

    public Layer getLayer() {
        return layer;
    }
}
