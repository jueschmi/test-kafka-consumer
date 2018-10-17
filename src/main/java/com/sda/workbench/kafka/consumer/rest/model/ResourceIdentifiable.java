package com.sda.workbench.kafka.consumer.rest.model;

import java.net.URI;
import javax.ws.rs.core.UriInfo;

@FunctionalInterface
public interface ResourceIdentifiable {

  URI link(UriInfo uriInfo);

}
