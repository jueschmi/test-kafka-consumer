package com.sda.workbench.kafka.consumer.streaming;

import javax.inject.Inject;

import com.sda.workbench.kafka.consumer.streaming.model.DocumentODSEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdase.framework.kafka.bundle.consumer.MessageHandler;

public class DocumentMessageHandler implements MessageHandler<String, DocumentODSEvent> {
   private static final Logger LOGGER = LoggerFactory.getLogger(DocumentMessageHandler.class);


   @Inject
   public DocumentMessageHandler() {
   }

   @Override
   public void handle(ConsumerRecord<String, DocumentODSEvent> record) {
      final DocumentODSEvent value = record.value();
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug("Incomming message , test-event-topic {} ,  key {} , value {}", record.topic(), record.key(),
                 value);
      }
   }
}
