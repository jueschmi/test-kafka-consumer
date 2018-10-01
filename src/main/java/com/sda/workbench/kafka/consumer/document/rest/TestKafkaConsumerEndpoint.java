package com.sda.workbench.kafka.consumer.document.rest;

import com.sda.avro.schema.dods.DocumentODSEvent;
import com.sda.avro.schema.dods.DocumentODSEventType;
import com.sda.avro.schema.dods.SoRKey;
import com.sda.workbench.kafka.consumer.AppConfiguration;
import com.sda.workbench.kafka.consumer.events.DocumentEventRepository;
import com.sdase.framework.kafka.bundle.KafkaBundle;
import com.sdase.framework.kafka.bundle.producer.MessageProducer;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
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
   public Response generateKafkaMessage() {

      if (producer != null) {
         String uuid = UUID.randomUUID().toString();
         DocumentODSEvent event = DocumentODSEvent.newBuilder().setType(DocumentODSEventType.document_create)
                 .setExternalId(SoRKey.newBuilder().setDOCID(uuid).build()).build();

         producer.send(uuid, event);
      }

      return Response.ok().build();
   }
}
