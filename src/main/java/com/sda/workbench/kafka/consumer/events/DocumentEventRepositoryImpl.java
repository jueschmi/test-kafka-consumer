package com.sda.workbench.kafka.consumer.events;

import com.sdase.avro.schema.dods.DocumentODSEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DocumentEventRepositoryImpl implements DocumentEventRepository {

  private final List<DocumentODSEvent> events;

  @Inject
  public DocumentEventRepositoryImpl() {
    events = new ArrayList<DocumentODSEvent>();
  }

  @Override
  public List<DocumentODSEvent> findAll() {
    return events;
  }

  @Override
  public DocumentODSEvent save(DocumentODSEvent event) {
    events.add(event);

    return event;
  }

  @Override
  public void deleteAll() {
    events.clear();
  }
}
