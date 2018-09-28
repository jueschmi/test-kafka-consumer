package com.sda.workbench.kafka.consumer.document.rest;

import com.sda.avro.schema.dods.DocumentODSEvent;
import com.sda.avro.schema.dods.DocumentODSEventType;
import com.sda.avro.schema.dods.SoRKey;
import com.sda.workbench.kafka.consumer.AppConfiguration;
import com.sdase.framework.kafka.bundle.KafkaBundle;
import com.sdase.framework.kafka.bundle.producer.MessageProducer;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.UUID;

public class TestKafkaConsumerEndpoint implements TestKafkaConsumerService {

   private final MessageProducer<String, DocumentODSEvent> producer;

   @Inject
   public TestKafkaConsumerEndpoint(MessageProducer<String, DocumentODSEvent> producer) {
      this.producer = producer;
   }

   @Override
   public Response checkKafkaMessages() {
      return Response.ok().build();
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
