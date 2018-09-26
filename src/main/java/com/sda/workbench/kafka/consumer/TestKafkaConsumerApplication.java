package com.sda.workbench.kafka.consumer;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sda.workbench.kafka.consumer.document.rest.TestKafkaConsumerEndpoint;
import com.sda.workbench.kafka.consumer.streaming.DocumentMessageHandler;
import com.sda.workbench.kafka.consumer.streaming.KafkaDeserializer;
import com.sdase.framework.dropwizard.weld.WeldBundle;
import com.sdase.framework.kafka.bundle.KafkaBundle;
import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import org.glassfish.jersey.server.ServerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zapodot.hystrix.bundle.HystrixBundle;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TestKafkaConsumerApplication extends Application<AppConfiguration> {
   public static final Logger LOGGER = LoggerFactory.getLogger(TestKafkaConsumerApplication.class);

   private static KafkaBundle<AppConfiguration> kafkaBundle = new KafkaBundle<>(AppConfiguration::getKafka);

   public static void main(final String[] args) throws Exception {
      new TestKafkaConsumerApplication().run(args);
   }

   @Override
   public void initialize(Bootstrap<AppConfiguration> bootstrap) {
      bootstrap.addBundle(kafkaBundle);
      bootstrap.addBundle(new WeldBundle(this));

      bootstrap.addBundle(HystrixBundle.withDefaultSettings());

      bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
            bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));
   }

   @Override
   public void run(final AppConfiguration configuration, final Environment environment) throws Exception {
      environment.jersey().register(new ApiListingResource());
      environment.jersey().register(TestKafkaConsumerEndpoint.class);
      environment.jersey().enable(ServerProperties.LOCATION_HEADER_RELATIVE_URI_RESOLUTION_DISABLED);
      environment.getObjectMapper().setSerializationInclusion(Include.NON_NULL);

      BeanConfig config = new BeanConfig();
      config.setScan(true);

      kafkaBundle.registerStringKeyMessageHandler(configuration.getTopics().testEventTopic,
            new KafkaDeserializer(), new DocumentMessageHandler());

   }

}
