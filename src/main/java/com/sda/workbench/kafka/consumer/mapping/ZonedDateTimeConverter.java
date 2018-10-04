package com.sda.workbench.kafka.consumer.mapping;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeConverter extends BidirectionalConverter<String, ZonedDateTime> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public ZonedDateTime convertTo(String source, Type<ZonedDateTime> destinationType, MappingContext mappingContext) {
        return source == null ? null : ZonedDateTime.parse(source, DATE_FORMATTER);
    }

    @Override
    public String convertFrom(ZonedDateTime source, Type<String> destinationType, MappingContext mappingContext) {
        return source != null ? source.toString() : null;
    }
}