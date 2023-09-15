package com.karthik.customemetric.custommetric.component;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class RequestCountMonitoringConfiguration extends WebMvcConfigurerAdapter {

    private MeterRegistry meterRegistry;
    private Counter totalCounter;
    @Autowired
    public RequestCountMonitoringConfiguration(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.totalCounter= this.meterRegistry.counter("dep_http_totalCount");
    }


    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public void afterCompletion(HttpServletRequest request,
                                        HttpServletResponse response, Object handler, Exception ex)
                    throws Exception {
                System.out.println("Counter incremented");
                totalCounter.increment();
            }
        });
    }
}
