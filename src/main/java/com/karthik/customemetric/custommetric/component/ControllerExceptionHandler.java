package com.karthik.customemetric.custommetric.component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {
    private MeterRegistry meterRegistry;
    private Counter errorCounter;

    @Autowired
    public ControllerExceptionHandler(MeterRegistry meterRegistry){
        this.meterRegistry = meterRegistry;
        this.errorCounter= this.meterRegistry.counter("dep_http_500_errorCount");
    }


    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleInternalError(Exception e) {
        countHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return "Internal Server Error";
    }

    private void countHttpStatus(HttpStatus status){
        errorCounter.increment();
    }
}
