package com.sda.workbench.kafka.consumer.mapping;

import com.sda.workbench.kafka.consumer.document.rest.model.SorKeyElement;
import com.sdase.avro.schema.dods.SoRKey;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

public class SoRKeyConverter extends
    BidirectionalConverter<com.sda.workbench.kafka.consumer.document.rest.model.SoRKey, SoRKey> {

  @Override
  public SoRKey convertTo(com.sda.workbench.kafka.consumer.document.rest.model.SoRKey source,
      Type<SoRKey> destinationType, MappingContext mappingContext) {
    if (source != null) {
      Map<String, String> sorKeyElements = source.getSorKeyElements().stream()
          .collect(Collectors.toMap(SorKeyElement::getKey, value -> value.getValue()));

      return SoRKey.newBuilder().setSorKeyElements(sorKeyElements).build();
    }

    return null;
  }

  @Override
  public com.sda.workbench.kafka.consumer.document.rest.model.SoRKey convertFrom(SoRKey source,
      Type<com.sda.workbench.kafka.consumer.document.rest.model.SoRKey> destinationType,
      MappingContext mappingContext) {
    if (source != null) {
      List<SorKeyElement> sorKeyElements = source.getSorKeyElements().entrySet()
          .stream()
          .map(m -> new SorKeyElement(m.getKey(), m.getValue()))
          .collect(Collectors.toList());

      return new com.sda.workbench.kafka.consumer.document.rest.model.SoRKey(sorKeyElements);
    }

    return null;
  }
}
