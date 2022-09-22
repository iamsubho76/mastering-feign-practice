package com.arnoldgalovics.inventory.service.processor;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Locale;

//https://www.logicbig.com/tutorials/spring-framework/spring-boot/custom-formatter.html
public class MillisToOffsetDateTimeFormatter implements Formatter<OffsetDateTime> {
    @Override
    public OffsetDateTime parse(String text, Locale locale) throws ParseException {
        long millis = Long.parseLong(text);
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneOffset.UTC);
    }

    @Override
    public String print(OffsetDateTime object, Locale locale) {
        return "" + object.toInstant().toEpochMilli();
    }
}
