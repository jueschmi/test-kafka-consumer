package com.sda.workbench.kafka.consumer.mapping;

import com.sda.workbench.kafka.consumer.document.rest.model.DocumentRest;
import com.sda.workbench.kafka.consumer.document.rest.model.DocumentRestCreate;
import com.sda.workbench.kafka.consumer.document.rest.model.DocumentRestDelete;
import com.sdase.avro.schema.dods.DocumentODSCreate;
import com.sdase.avro.schema.dods.DocumentODSDelete;
import com.sdase.avro.schema.dods.DocumentODSEvent;
import com.sdase.avro.schema.dods.DocumentODSEventType;
import com.sdase.framework.mapping.filter.FieldQueryFilter;
import java.time.ZonedDateTime;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@ApplicationScoped
public class DocumentODSMapper {

  private MapperFacade mapper;

  private MappingContext.Factory mappingContextFactory;

  @PostConstruct
  public void init() {
    mappingContextFactory = new MappingContext.Factory();
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
        .mappingContextFactory(mappingContextFactory)
        .mapNulls(false)
        .build();
    mapperFactory.getConverterFactory()
        .registerConverter(new PassThroughConverter(ZonedDateTime.class));
    mapperFactory.getConverterFactory().registerConverter(new ZonedDateTimeConverter());
    mapperFactory.getConverterFactory().registerConverter(new SoRKeyConverter());
    mapperFactory.registerFilter(new FieldQueryFilter());

    registerDocumentODSEventMapper(mapperFactory);

    this.mapper = mapperFactory.getMapperFacade();

  }

  private void registerDocumentODSEventMapper(MapperFactory mapperFactory) {
    // DocumentODSCreate Mapping
    mapperFactory
        .classMap(DocumentODSCreate.class, DocumentRestCreate.class)
        .customize(new DocumentODSCreateMapper())
        .byDefault()
        .register();

    // DocumentODSDelete Mapping
    mapperFactory
        .classMap(DocumentODSDelete.class, DocumentRestDelete.class)
        .customize(new DocumentODSDeleteMapper())
        .byDefault()
        .register();
  }

  public DocumentRest mapToRestModel2(final DocumentODSEvent documentEvent) {
    if (documentEvent.getType() == DocumentODSEventType.DOCUMENT_CREATE) {
      return mapper.map(documentEvent.getPayload(), DocumentRestCreate.class);
    }
    if (documentEvent.getType() == DocumentODSEventType.DOCUMENT_DELETE) {
      return mapper.map(documentEvent.getPayload(), DocumentRestDelete.class);
    }
    return null;
  }

  public MappingContext.Factory getMappingContextFactory() {
    return mappingContextFactory;
  }

  public MapperFacade getMapper() {
    return mapper;
  }
}