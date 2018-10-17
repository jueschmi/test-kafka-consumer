package com.sda.workbench.kafka.consumer.document.rest;

import com.sda.workbench.kafka.consumer.TestKafkaConsumerFacade;
import com.sda.workbench.kafka.consumer.document.rest.model.DocumentRest;
import com.sdase.framework.mapping.jaxrs.fields.FieldFiltered;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("kafka")
@Api
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface TestKafkaConsumerService extends TestKafkaConsumerFacade {

  @GET
  @Path("/check")
  @ApiOperation(value = "Returns the document details.", response = DocumentRest.class, nickname = "fetchDocument", tags = {
      "document"})
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", required = true, value = "Bearer: xxxxxx.yyyyyyy.zzzzzz", dataType = "string", paramType = "header")})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Returns the requested docuement.", response = DocumentRest.class),
      @ApiResponse(code = 404, message = "The requested document was not found.", response = DocumentRest.class)})
  @FieldFiltered
  @Produces(MediaType.APPLICATION_JSON)
  Response checkKafkaMessage();

  @GET
  @Path("/checkmessages")
  @Produces(MediaType.APPLICATION_JSON)
  List<DocumentRest> checkKafkaMessages();


  @POST
  @Path("/testmessage")
  Response generateKafkaMessage(
      @ApiParam(type = "tpye of the generated message") @DefaultValue("create") @QueryParam("type") String messagetype);

  @POST
  @Path("/resetmessages")
  Response resetReceivedMessages();
}
