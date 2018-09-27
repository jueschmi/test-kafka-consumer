package com.sda.workbench.kafka.consumer.streaming;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaTopic {
   @JsonProperty("test-event-topic")
   public String testEventTopic;

   @JsonProperty("test-error-topic")
   public String testErrorTopic;
}
