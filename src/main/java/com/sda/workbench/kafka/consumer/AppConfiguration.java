package com.sda.workbench.kafka.consumer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sda.workbench.kafka.consumer.streaming.KafkaTopic;

import com.sdase.framework.kafka.bundle.KafkaConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

public class AppConfiguration extends Configuration {
   @Valid
   @NotNull
   private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

   @Valid
   @NotNull
   @JsonProperty("kafka")
   private KafkaConfiguration kafka = new KafkaConfiguration();

   @Valid
   @NotNull
   @JsonProperty("topics")
   private KafkaTopic topics = new KafkaTopic();

   public JerseyClientConfiguration getHttpClient() {
      return httpClient;
   }

   public void setHttpClient(JerseyClientConfiguration httpClient) {
      this.httpClient = httpClient;
   }

   public KafkaConfiguration getKafka() {
      return kafka;
   }

   public void setKafka(final KafkaConfiguration kafka) {
      this.kafka = kafka;
   }

   public KafkaTopic getTopics() {
      return topics;
   }

   public void setTopics(KafkaTopic topics) {
      this.topics = topics;
   }
}
