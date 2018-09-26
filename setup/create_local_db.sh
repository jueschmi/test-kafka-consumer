#!/bin/bash

MONGO_1="mongo-1"
MONGO_1_PORT="27118"
MONGO_2="mongo-2"
MONGO_2_PORT="27119"
MONGO_3="mongo-3"
MONGO_3_PORT="27120"
MONGO_CONTAINER=
PRIMARY_PORT=$(echo `docker exec -it mongo-1 bash -c "mongo --port 27118 --eval \"rs.isMaster().primary\"" | tail -1 | cut -d ":" -f 2` | tr -d '\r')

case $PRIMARY_PORT in
    $MONGO_1_PORT)
        MONGO_CONTAINER=${MONGO_1}
        ;;
    $MONGO_2_PORT)
        MONGO_CONTAINER=${MONGO_2}
        ;;
    $MONGO_3_PORT)
        MONGO_CONTAINER=${MONGO_3}
        ;;
    *)
        echo "Error: didn't match"
        ;;    
esac

echo "Mongo primary found: ${MONGO_CONTAINER} on port \"${PRIMARY_PORT}\""

echo "Prepare db script"
cat create_document_ods_db.template | sed -e "s/_MONGO_CONTAINER_/${MONGO_CONTAINER}/" -e "s/_PRIMARY_PORT_/${PRIMARY_PORT}/" > create_document_ods_db.js

echo "Copy db script to docker container ${MONGO_CONTAINER}"
docker cp create_document_ods_db.js ${MONGO_CONTAINER}:/create_document_ods_db.js

echo "Execute db script on docker container ${MONGO_CONTAINER}"
docker exec -it ${MONGO_CONTAINER} bash -c "mongo --port ${PRIMARY_PORT} < create_document_ods_db.js"

echo "Cleanup"
rm create_document_ods_db.js
