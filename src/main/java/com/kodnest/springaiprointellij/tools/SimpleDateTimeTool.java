package com.kodnest.springaiprointellij.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.LogManager;

public class SimpleDateTimeTool {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //informational tool
    @Tool(description = "Get The local Date And Time From the users Zone")
    public String dateAndTimeRetrival() {
        logger.info("Tool Calling");
        logger.info("Get the local Date and Time");
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }

    //action tool

    @Tool(description = "Set the alarm")
    public void setAlaram(@ToolParam(description = "Time in ISO-8601 format") String time) {
        var dateTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        logger.info("set the time {}",dateTime);
    }
}
