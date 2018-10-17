package com.sda.workbench.kafka.consumer.document.rest.model;

import com.sda.workbench.kafka.consumer.rest.model.ResourceIdentifiable;
import io.swagger.annotations.ApiModelProperty;
import java.net.URI;
import javax.ws.rs.core.UriInfo;
import org.hibernate.validator.constraints.NotEmpty;

public abstract class DocumentRest implements ResourceIdentifiable {

  @NotEmpty
  @ApiModelProperty(value = "The internal document id.", example = "...", required = true)
  private String eventType;

  @NotEmpty
  @ApiModelProperty(value = "The internal document id.", example = "...", required = true)
  private String uuid;

  @NotEmpty
  @ApiModelProperty(value = "The external document id.", example = "...", required = true)
  private SoRKey externalId;

  @Override
  public URI link(UriInfo uriInfo) {
    return uriInfo.getBaseUriBuilder().path("documents").path(this.uuid).build();
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public SoRKey getExternalId() {
    return externalId;
  }

  public void setExternalId(SoRKey externalId) {
    this.externalId = externalId;
  }
}
