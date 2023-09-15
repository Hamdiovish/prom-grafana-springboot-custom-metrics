package com.karthik.customemetric.custommetric.controller;
import com.karthik.customemetric.custommetric.component.MessageCustomMetric;
import com.karthik.customemetric.custommetric.service.DataService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class DataController {

    private MessageCustomMetric messageCustomMetric;
    private final AtomicLong counter = new AtomicLong();
    private static final String template = "{\"action\": %s}";
    @Autowired
    public DataController(MessageCustomMetric messageCustomMetric) {
        this.messageCustomMetric = messageCustomMetric;
    }

        @GetMapping("/sample")
        @Timed()
        public DataService index(@RequestParam(value = "action", defaultValue = "200") String action) throws Exception {
            messageCustomMetric.longRunnerOperation();
            if (!action.equalsIgnoreCase("200")){
                throw new Exception();
            }else {
                return new DataService(counter.incrementAndGet(), String.format(template, action));
            }
        }
}

