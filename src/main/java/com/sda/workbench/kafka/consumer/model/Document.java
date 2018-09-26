package com.sda.workbench.kafka.consumer.model;

import java.time.LocalDate;
import java.util.UUID;

import org.mongodb.morphia.annotations.Id;

public class Document {

   @Id
   private String uuid;

   private String title;

   private String category;

   private String type;

   private LocalDate date;

   private LocalDate dateIn;

   private String bundleId;

   private String orginalSender;

   private String orginalReceiver;

   public Document() {
      uuid = UUID.randomUUID().toString();
   }

   public String getUuid() {
      return uuid;
   }

   public void setUuid(String uuid) {
      this.uuid = uuid;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getCategory() {
      return category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public String getType() {
      return type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public LocalDate getDate() {
      return date;
   }

   public void setDate(LocalDate date) {
      this.date = date;
   }

   public LocalDate getDateIn() {
      return dateIn;
   }

   public void setDateIn(LocalDate dateIn) {
      this.dateIn = dateIn;
   }

   public String getBundleId() {
      return bundleId;
   }

   public void setBundleId(String bundleId) {
      this.bundleId = bundleId;
   }

   public String getOrginalSender() {
      return orginalSender;
   }

   public void setOrginalSender(String orginalSender) {
      this.orginalSender = orginalSender;
   }

   public String getOrginalReceiver() {
      return orginalReceiver;
   }

   public void setOrginalReceiver(String orginalReceiver) {
      this.orginalReceiver = orginalReceiver;
   }

}
