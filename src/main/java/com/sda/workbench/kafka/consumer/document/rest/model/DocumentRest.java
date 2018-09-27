package com.sda.workbench.kafka.consumer.document.rest.model;

import java.net.URI;

import javax.ws.rs.core.UriInfo;

import com.sda.workbench.kafka.consumer.rest.model.ResourceIdentifiable;

import io.swagger.annotations.ApiModelProperty;

public class DocumentRest implements ResourceIdentifiable {

   @ApiModelProperty(value = "The document id.", example = "uuid-document-1234", required = true)
   private String uuid;

   @ApiModelProperty(value = "The document title.", example = "AVB ")
   private String title;

   @ApiModelProperty(value = "The category of document.", example = "Haftpflichtversicherung")
   private String category;

   @ApiModelProperty(value = "The type of document.", example = "Schadenmeldung, Police , Rechnung")
   private String type;

   @ApiModelProperty(value = "Date of the document printed on the document", example = "2017-11-11")
   private String date;

   @ApiModelProperty(value = "Date of receipt at VU", example = "2017-12-12")
   private String dateIn;

   @ApiModelProperty(value = "All documents belonging to a document bundle have the same unique Id", example = "12dw1w-12s-")
   private String bundleId;

   @ApiModelProperty(value = "The Sender according to document", example = "First name Last name or company, if necessary city")
   private String orginalSender;

   @ApiModelProperty(value = "The Recipient according to document.", example = "First name Last name or company, if necessary city")
   private String orginalReceiver;

   @Override
   public URI link(UriInfo uriInfo) {
      return uriInfo.getBaseUriBuilder().path("documents").path(this.uuid).build();
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

   public String getDate() {
      return date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getDateIn() {
      return dateIn;
   }

   public void setDateIn(String dateIn) {
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
