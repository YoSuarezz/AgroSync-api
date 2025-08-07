package com.agrosync.infrastructure.rest.advice;

import java.util.Map;

public interface ParameterReceiver {
    void setParameters(Map<String, Object> parameters);
}
