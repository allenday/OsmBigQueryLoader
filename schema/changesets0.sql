WITH
relations AS (
SELECT
  changeset,
  MIN(osm_timestamp) AS osm_timestamp_min,
  MAX(osm_timestamp) AS osm_timestamp_max,
  ARRAY_AGG(id) AS relations
FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_relations0`
GROUP BY changeset
),
ways AS (
SELECT
  changeset,
  MIN(osm_timestamp) AS osm_timestamp_min,
  MAX(osm_timestamp) AS osm_timestamp_max,
  ARRAY_AGG(id) AS ways
FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_ways0`
GROUP BY changeset
),
nodes AS (
SELECT
  changeset,
  MIN(CAST(osm_timestamp/1000 AS INT64)) AS osm_timestamp_min,
  MAX(CAST(osm_timestamp/1000 AS INT64)) AS osm_timestamp_max,
  ARRAY_AGG(id) AS nodes
FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_nodes0`
GROUP BY changeset
),
times AS (
  SELECT
  changeset,
  MIN(osm_timestamp_min) AS osm_timestamp_min,
  MAX(osm_timestamp_min) AS osm_timestamp_max
  FROM (
    SELECT changeset, osm_timestamp_min, osm_timestamp_max FROM relations
    UNION ALL
    SELECT changeset, osm_timestamp_min, osm_timestamp_max FROM ways
    UNION ALL
    SELECT changeset, osm_timestamp_min, osm_timestamp_max FROM nodes
  ) AS x GROUP BY changeset
)

SELECT
  times.changeset,
  times.osm_timestamp_min AS osm_timestamp_min,
  times.osm_timestamp_max AS osm_timestamp_max,
  relations.relations AS relations,
  ways.ways AS ways,
  nodes.nodes AS nodes
FROM
  times
  FULL OUTER JOIN nodes USING(changeset)
  FULL OUTER JOIN ways USING(changeset)
  FULL OUTER JOIN relations USING(changeset)
WHERE
  TRUE
--  AND ARRAY_LENGTH(nodes.nodes) > 0
--  AND ARRAY_LENGTH(ways.ways) > 0 
--  AND ARRAY_LENGTH(relations.relations) > 0 
--LIMIT 1000

