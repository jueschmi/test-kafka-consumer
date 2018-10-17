package com.sda.workbench.kafka.consumer;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(info = @Info(description =
    "# Customer ODS API \nThe service provides information of a customer including the contract overview as well as the contract details. A specific contract identifier is used to identify the customer and the contracts."
        + " For accessing the resources a valid JSON Web Token (JWT) must be passed in all the queries in the 'Authorization' header."
        + " A JWT needs to be generated by the consumer by passing a valid user and password.", //
    version = "V1.0.0", //
    title = "Customer ODS Service", //
    termsOfService = "SDA terms of service URL", //
    contact = @Contact(name = "SDA", email = "sda@swagger.io", url = "http://swagger.io"), //
    license = @License(name = "SDA Non Public License", url = "http://swagger.io") //
), //
    consumes = {"application/json"}, //
    produces = {"application/json"}, //
    schemes = {SwaggerDefinition.Scheme.HTTP})
public interface TestKafkaConsumerFacade {

}
