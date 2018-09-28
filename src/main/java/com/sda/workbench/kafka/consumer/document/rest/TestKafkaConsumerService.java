package com.sda.workbench.kafka.consumer.document.rest;

import com.sda.workbench.kafka.consumer.TestKafkaConsumerFacade;
import com.sda.workbench.kafka.consumer.document.rest.model.DocumentRest;
import com.sdase.framework.mapping.jaxrs.fields.FieldFiltered;
import io.swagger.annotations.*;

import javax.ws.rs.*;
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
         "document" })
   @ApiImplicitParams({
         @ApiImplicitParam(name = "Authorization", required = true, value = "Bearer: xxxxxx.yyyyyyy.zzzzzz", dataType = "string", paramType = "header") })
   @ApiResponses(value = {
         @ApiResponse(code = 200, message = "Returns the requested docuement.", response = DocumentRest.class),
         @ApiResponse(code = 404, message = "The requested document was not found.", response = DocumentRest.class) })
   @FieldFiltered
   @Produces(MediaType.APPLICATION_JSON)
   Response checkKafkaMessages();

   @POST
   @Path("/testmessage")
   Response generateKafkaMessage();

}
