echo "
SELECT id, version, username, changeset, visible, TIMESTAMP_SECONDS(CAST(osm_timestamp/1000 AS INT64)) AS osm_timestamp, ST_GEOGPOINT(longitude, latitude) AS geometry, all_tags FROM \`${PROJECT}.${DATASET}.${PREFIX}_nodes0\`
"

