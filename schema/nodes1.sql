INSERT INTO `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_nodes1`
SELECT id, version, username, changeset, visible, TIMESTAMP_SECONDS(CAST(osm_timestamp/1000 AS INT64)) AS osm_timestamp, ST_GEOGPOINT(longitude, latitude) AS geometry, all_tags FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_nodes0`
