package com.sda.workbench.kafka.consumer.mapping;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.sda.avro.schema.dods.DocumentODSCreate;
import com.sda.avro.schema.dods.DocumentODSDelete;
import com.sda.avro.schema.dods.RelevantPartner;
import com.sda.workbench.kafka.consumer.document.rest.model.DocumentRestCreate;
import com.sda.workbench.kafka.consumer.document.rest.model.DocumentRestDelete;
import com.sda.workbench.kafka.consumer.document.rest.model.SoRKey;
import com.sda.workbench.kafka.consumer.document.rest.model.SorKeyElement;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

public class DocumentODSDeleteMapper extends CustomMapper<DocumentODSDelete, DocumentRestDelete> {

    @Override
    public void mapAtoB(DocumentODSDelete a, DocumentRestDelete b, MappingContext mappingContext){
        b.setEventType("document_delete");

        b.setUuid(a.getUuid());

        List<SorKeyElement> sorKeyElements = a.getExternalId().getSorKeyElements().entrySet()
                .stream()
                .map(m->new SorKeyElement(m.getKey(), m.getValue()))
                .collect(Collectors.toList());

        b.setExternalId(new SoRKey(sorKeyElements));
    }

}