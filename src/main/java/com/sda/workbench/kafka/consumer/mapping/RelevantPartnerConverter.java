package com.sda.workbench.kafka.consumer.mapping;

import com.sda.avro.schema.dods.RelevantPartner;
import com.sda.avro.schema.dods.SoRKey;
import com.sda.workbench.kafka.consumer.document.rest.model.SorKeyElement;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelevantPartnerConverter extends BidirectionalConverter<com.sda.workbench.kafka.consumer.document.rest.model.RelevantPartner, RelevantPartner> {
    @Override
    public RelevantPartner convertTo(com.sda.workbench.kafka.consumer.document.rest.model.RelevantPartner source, Type<RelevantPartner> destinationType, MappingContext mappingContext) {
        if (source != null) {
//            Map<String, String> sorKeyElements = source.getSorKeyElements().stream()
//                    .collect(Collectors.toMap(SorKeyElement::getKey, value -> value.getValue()));
//
//            return SoRKey.newBuilder().setSorKeyElements(sorKeyElements).build();
        }

        return null;
    }

    @Override
    public com.sda.workbench.kafka.consumer.document.rest.model.RelevantPartner convertFrom(RelevantPartner source, Type<com.sda.workbench.kafka.consumer.document.rest.model.RelevantPartner> destinationType, MappingContext mappingContext) {
        if (source != null) {
//            List<SorKeyElement> sorKeyElements = source.getSorKeyElements().entrySet()
//                    .stream()
//                    .map(m -> new SorKeyElement(m.getKey(), m.getValue()))
//                    .collect(Collectors.toList());
//
//            return new com.sda.workbench.kafka.consumer.document.rest.model.SoRKey(sorKeyElements);
        }

        return null;
    }
}