package com.sda.workbench.kafka.consumer.streaming.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DocumentODSEvent {

   private String uuid;

   public void setUuid(final String uuid) {
      this.uuid = uuid;
   }

   public String getUuid() {
      return uuid;
   }

   @Override
   public String toString() {
      return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
   }

}
