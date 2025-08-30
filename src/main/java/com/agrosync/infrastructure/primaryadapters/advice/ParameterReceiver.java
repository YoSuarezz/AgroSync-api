package com.agrosync.infrastructure.primaryadapters.advice;

import java.util.Map;

public interface ParameterReceiver {
    void setParameters(Map<String, Object> parameters);
}
