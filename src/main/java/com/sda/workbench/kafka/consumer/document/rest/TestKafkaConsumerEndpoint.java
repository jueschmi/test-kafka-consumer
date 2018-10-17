package com.sda.workbench.kafka.consumer.document.rest;

import com.sda.workbench.kafka.consumer.document.rest.model.DocumentRest;
import com.sda.workbench.kafka.consumer.events.DocumentEventRepository;
import com.sda.workbench.kafka.consumer.mapping.DocumentODSMapper;
import com.sdase.avro.schema.dods.DocumentODSCreate;
import com.sdase.avro.schema.dods.DocumentODSDelete;
import com.sdase.avro.schema.dods.DocumentODSEvent;
import com.sdase.avro.schema.dods.DocumentODSEventType;
import com.sdase.avro.schema.dods.InOutBoundType;
import com.sdase.avro.schema.dods.PartnerRoleType;
import com.sdase.avro.schema.dods.RelevantPartner;
import com.sdase.avro.schema.dods.SoRKey;
import com.sdase.framework.kafka.bundle.producer.MessageProducer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class TestKafkaConsumerEndpoint implements TestKafkaConsumerService {

  private final MessageProducer<String, DocumentODSEvent> producer;
  private final DocumentEventRepository eventRepository;

  @Inject
  private DocumentODSMapper odsMapper;

  @Inject
  public TestKafkaConsumerEndpoint(final MessageProducer<String, DocumentODSEvent> producer,
      final DocumentEventRepository eventRepository) {
    this.producer = producer;
    this.eventRepository = eventRepository;
  }

  @Override
  public Response checkKafkaMessage() {
    return Response.ok().build();
  }

  @Override
  public List<DocumentRest> checkKafkaMessages() {
    List<DocumentODSEvent> result = eventRepository.findAll();

    if (!result.isEmpty()) {

      List<DocumentRest> restDocs = result.stream()
          .map(d -> odsMapper.mapToRestModel2(d))
          .collect(Collectors.toList());

      return restDocs;
    }

    return new ArrayList<DocumentRest>();

//      List<String> collect = result.stream().map(s -> s.toString()).collect(Collectors.toList());
//      return collect;
  }

  @Override
  public Response generateKafkaMessage(String messagetype) {

    if (producer != null) {
      final String uuid = UUID.randomUUID().toString();
      final Map<String, String> sorKeyElements = Collections.singletonMap("DOCID", uuid);
      final SoRKey sorKey = SoRKey.newBuilder().setSorKeyElements(sorKeyElements).build();

      DocumentODSEvent event = null;
      switch (messagetype) {
        case "create":
          final ArrayList<String> classificationIds = new ArrayList();
          classificationIds.add("1");
          classificationIds.add("2");

          final ZonedDateTime zdt = Instant.now().atZone(ZoneId.of("Europe/Paris"));
          final String sZDT = zdt.toString();

          final List<RelevantPartner> relevantPartners = new ArrayList<>();

          final RelevantPartner partner = RelevantPartner.newBuilder()
              .setPartnerId(UUID.randomUUID().toString()).setPartnerRole(
                  PartnerRoleType.CUSTOMER).build();
          relevantPartners.add(partner);

          event = DocumentODSEvent.newBuilder().setType(DocumentODSEventType.DOCUMENT_CREATE)
              .setPayload(DocumentODSCreate.newBuilder().setId(uuid).setExternalId(sorKey)
                  .setTitle("KV Rechnung")
                  .setCategory("Krankenversicherung")
                  //.setType("Rechnung")
                  //.setDate(sZDT)
                  .setClassificationType("classfication type???")
                  .setClassificationIds(classificationIds)
                  .setInOutBound(InOutBoundType.INBOUND)
                  .setRelevantPartners(relevantPartners)
                  .setBusinessTransactionId("123").build()).build();
          break;
        case "delete":
          event = DocumentODSEvent.newBuilder().setType(DocumentODSEventType.DOCUMENT_DELETE)
              .setPayload(DocumentODSDelete.newBuilder().setId(uuid).setExternalId(sorKey)
                  .build()).build();
          break;


      }

      producer.send(uuid, event);
    }

    return Response.ok().build();
  }

  @Override
  public Response resetReceivedMessages() {
    eventRepository.deleteAll();

    return Response.ok().build();
  }
}
