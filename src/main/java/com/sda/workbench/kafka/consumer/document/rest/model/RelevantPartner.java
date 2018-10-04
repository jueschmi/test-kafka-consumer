package com.sda.workbench.kafka.consumer.document.rest.model;

import io.swagger.annotations.ApiModelProperty;

public class RelevantPartner {

    @ApiModelProperty(value = "A list of classification ids.", example = "...")
    private SoRKey partnerId;

    @ApiModelProperty(value = "The role of the partner in the contecxt of the document.", example = "...")
    private String partnerRole;

    public SoRKey getPartnerId() { return partnerId; }

    public void setPartnerId(SoRKey partnerId) { this.partnerId = partnerId; }

    public String getPartnerRole() { return partnerRole; }

    public void setPartnerRole(String partnerRole) { this.partnerRole = partnerRole; }
}
