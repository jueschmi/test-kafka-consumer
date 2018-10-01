package com.sda.workbench.kafka.consumer.events;

import com.sda.avro.schema.dods.DocumentODSEvent;

import java.util.List;

public interface DocumentEventRepository {

    List<DocumentODSEvent> findAll();

    DocumentODSEvent save(DocumentODSEvent event);

    void deleteAll();
}
