package com.sda.workbench.kafka.consumer.document.rest;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class TestKafkaConsumerEndpoint implements TestKafkaConsumerService {

   @Inject
   public TestKafkaConsumerEndpoint() {
   }

   @Override
   public Response checkKafkaMessages() {
      return Response.ok().build();
   }

}
