package com.sda.workbench.kafka.consumer.streaming;

import java.util.Map;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.sda.workbench.kafka.consumer.streaming.model.DocumentODSEvent;
import com.sdase.framework.mapping.json.SDAJsonMapper;
import com.sdase.framework.mapping.json.SDAJsonMapperImpl;

public class KafkaDeserializer implements Deserializer<DocumentODSEvent> {

   private final SDAJsonMapper mapper;

   public KafkaDeserializer() {
      mapper = new SDAJsonMapperImpl();
   }

   @Override
   public void configure(final Map configs, boolean isKey) {
      // No Configuration required for this class
   }

   @Override
   public DocumentODSEvent deserialize(String topic, byte[] data) {
      if (data == null)
         return null;
      try {
         return mapper.readValue(data, DocumentODSEvent.class);
      } catch (Exception e) {
         throw new SerializationException("Error serialization streamingSerializer. message: ", e);
      }
   }

   @Override
   public void close() {
      // Do nothing because of not needed.
   }

}
