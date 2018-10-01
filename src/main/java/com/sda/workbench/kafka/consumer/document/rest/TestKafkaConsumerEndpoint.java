package com.sda.workbench.kafka.consumer.document.rest;

import com.sda.avro.schema.dods.*;
import com.sda.workbench.kafka.consumer.AppConfiguration;
import com.sda.workbench.kafka.consumer.events.DocumentEventRepository;
import com.sdase.framework.kafka.bundle.KafkaBundle;
import com.sdase.framework.kafka.bundle.producer.MessageProducer;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TestKafkaConsumerEndpoint implements TestKafkaConsumerService {

   private final MessageProducer<String, DocumentODSEvent> producer;
   private final DocumentEventRepository eventRepository;

   @Inject
   public TestKafkaConsumerEndpoint(final MessageProducer<String, DocumentODSEvent> producer, final DocumentEventRepository eventRepository) {
      this.producer = producer;
      this.eventRepository =  eventRepository;
   }

   @Override
   public Response checkKafkaMessage() {
      return Response.ok().build();
   }

   @Override
   public List<String> checkKafkaMessages() {
      List<DocumentODSEvent> result = eventRepository.findAll();

      List<String> collect = result.stream().map(s -> s.toString()).collect(Collectors.toList());

      return collect;
   }

   @Override
   public Response generateKafkaMessage(String messagetype) {

      if (producer != null) {
         final String uuid = UUID.randomUUID().toString();
         DocumentODSEvent event = null;
         switch (messagetype) {
            case "create":
               ArrayList<String> classificationIds = new ArrayList();
               classificationIds.add("1");
               classificationIds.add("2");

               event = DocumentODSEvent.newBuilder().setType(DocumentODSEventType.document_create)
                       .setPayload(DocumentODSCreate.newBuilder().setUuid(uuid).setExternalId(SoRKey.newBuilder().setDOCID(uuid).build())
                               .setTitle("Test Document Title")
                               .setCategory("Haftpflicht")
                               .setClassificationType("classfication type???")
                               .setClassificationIds(classificationIds)
                               .setInOutBound(1)
                               .setRelevantPartnerId("123456")
                               .setBusinessTransactionId("123").build()).build();
               break;
            case "delete":
               event = DocumentODSEvent.newBuilder().setType(DocumentODSEventType.document_delete)
                       .setPayload(DocumentODSDelete.newBuilder().setUuid(uuid).setExternalId(SoRKey.newBuilder().setDOCID(uuid).build())
                               .build()).build();
               break;


         }

         producer.send(uuid, event);
      }

      return Response.ok().build();
   }
}
