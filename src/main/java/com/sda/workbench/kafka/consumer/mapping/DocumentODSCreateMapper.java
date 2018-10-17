package com.sda.workbench.kafka.consumer.mapping;

import com.sda.workbench.kafka.consumer.document.rest.model.DocumentRestCreate;
import com.sda.workbench.kafka.consumer.document.rest.model.SoRKey;
import com.sda.workbench.kafka.consumer.document.rest.model.SorKeyElement;
import com.sdase.avro.schema.dods.DocumentODSCreate;
import com.sdase.avro.schema.dods.RelevantPartner;
import java.util.List;
import java.util.stream.Collectors;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class DocumentODSCreateMapper extends CustomMapper<DocumentODSCreate, DocumentRestCreate> {

  @Override
  public void mapAtoB(DocumentODSCreate a, DocumentRestCreate b, MappingContext mappingContext) {
    b.setEventType("document_create");

    b.setUuid(a.getId());
    b.setTitle(a.getTitle());
    b.setCategory(a.getCategory());
//        b.setDate( (a.getDate() != null) ? ZonedDateTime.parse(a.getDate()) : null);
//        b.setDateIn( (a.getDateIn() != null) ? ZonedDateTime.parse(a.getDateIn()) : null);
//        b.setBundleId(a.getBundleId());
//        b.setOriginalSender(a.getOriginalSender());
//        b.setOriginalReceiver(a.getOriginalReceiver());
    b.setClassificationType(a.getClassificationType());
    b.setClassificationIds(a.getClassificationIds());
    b.setProtectionClass(a.getProtectionClass() != null ? a.getProtectionClass().intValue() : 1);

    List<SorKeyElement> sorKeyElements = a.getExternalId().getSorKeyElements().entrySet()
        .stream()
        .map(m -> new SorKeyElement(m.getKey(), m.getValue()))
        .collect(Collectors.toList());

    b.setExternalId(new SoRKey(sorKeyElements));

    List<com.sda.workbench.kafka.consumer.document.rest.model.RelevantPartner> relevantPartners = a
        .getRelevantPartners()
        .stream()
        .map(partner -> convertDODSRelevantPartner(partner))
        .collect(Collectors.toList());

    b.setRelevantPartners(relevantPartners);
  }

  private com.sda.workbench.kafka.consumer.document.rest.model.RelevantPartner convertDODSRelevantPartner(
      RelevantPartner relevantPartner) {
    com.sda.workbench.kafka.consumer.document.rest.model.RelevantPartner retPartner = new com.sda.workbench.kafka.consumer.document.rest.model.RelevantPartner();
    retPartner.setPartnerRole(relevantPartner.getPartnerRole().toString());
    retPartner.setPartnerId(relevantPartner.getPartnerId());

    return retPartner;
  }
}