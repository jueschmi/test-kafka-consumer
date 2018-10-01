package com.sda.workbench.kafka.consumer.events;

import com.sda.avro.schema.dods.DocumentODSEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
