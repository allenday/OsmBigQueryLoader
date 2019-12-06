#!/bin/bash

export PREFIX=history
export BUCKET=gs://openstreetmap-public-data-dev-preprocessed/$PREFIX
export PROJECT=openstreetmap-public-data-dev 
export DATASET=osmbigqueryloader_dev 
export HEADLESS=""

#bq rm --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" ${PREFIX}_ways0
#bq rm --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" ${PREFIX}_ways1
#bq rm --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" ${PREFIX}_relations0
#bq rm --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" ${PREFIX}_relations1
#bq rm --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" ${PREFIX}_changesets0

# === nodes0 ===
#bq rm --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" ${PREFIX}_nodes0
#bq mk --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" --schema ddl/nodes0.json ${PREFIX}_nodes0
#bq load ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" --source_format NEWLINE_DELIMITED_JSON ${PREFIX}_nodes0 ${BUCKET}/node.json ddl/nodes0.json
# === ways0 ===
#bq rm --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" ${PREFIX}_ways0
#bq mk --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" --schema ddl/ways0.json ${PREFIX}_ways0
#bq load ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" --source_format NEWLINE_DELIMITED_JSON ${PREFIX}_ways0 ${BUCKET}/way.json ddl/ways0.json
# === relations0 ===
#bq rm --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" ${PREFIX}_relations0
#bq mk --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" --schema ddl/relations0.json ${PREFIX}_relations0
#bq load ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" --source_format NEWLINE_DELIMITED_JSON ${PREFIX}_relations0 ${BUCKET}/relation.json ddl/relations0.json

# === changesets0 ===
#bq rm --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" ${PREFIX}_changesets0
#bq mk --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" --schema ddl/changesets0.json ${PREFIX}_changesets0
#bash ./dml/changesets0.sql.sh | bq query ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" --nouse_legacy_sql --destination_table ${PREFIX}_changesets0

# === nodes1 ===
#bq rm --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" ${PREFIX}_nodes1
#bq mk --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" --schema ddl/nodes1.json ${PREFIX}_nodes1
#bash ./dml/nodes1.sql.sh | bq query ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" --nouse_legacy_sql --destination_table ${PREFIX}_nodes1
# === ways1 ===
bq rm --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" ${PREFIX}_ways1
bq mk --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" --schema ddl/ways1.json ${PREFIX}_ways1
bash ./dml/ways1.sql.sh | bq query ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" --nouse_legacy_sql --destination_table ${PREFIX}_ways1







#cat ddl/nodes1.json | bq mk --force ${HEADLESS} --dataset_id "${PROJECT}:${DATASET}" --schema ddl/nodes1.json ${PREFIX}_nodes1

