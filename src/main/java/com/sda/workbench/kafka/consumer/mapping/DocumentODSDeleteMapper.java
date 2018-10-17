package com.sda.workbench.kafka.consumer.mapping;

import com.sda.workbench.kafka.consumer.document.rest.model.DocumentRestDelete;
import com.sda.workbench.kafka.consumer.document.rest.model.SoRKey;
import com.sda.workbench.kafka.consumer.document.rest.model.SorKeyElement;
import com.sdase.avro.schema.dods.DocumentODSDelete;
import java.util.List;
import java.util.stream.Collectors;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class DocumentODSDeleteMapper extends CustomMapper<DocumentODSDelete, DocumentRestDelete> {

  @Override
  public void mapAtoB(DocumentODSDelete a, DocumentRestDelete b, MappingContext mappingContext) {
    b.setEventType("document_delete");

    b.setUuid(a.getId());

    List<SorKeyElement> sorKeyElements = a.getExternalId().getSorKeyElements().entrySet()
        .stream()
        .map(m -> new SorKeyElement(m.getKey(), m.getValue()))
        .collect(Collectors.toList());

    b.setExternalId(new SoRKey(sorKeyElements));
  }

}