echo "
WITH
relations AS (
SELECT
  changeset,
  MIN(osm_timestamp) AS osm_timestamp_min,
  MAX(osm_timestamp) AS osm_timestamp_max,
  ARRAY_AGG(id) AS relations
FROM \`${PROJECT}.${DATASET}.${PREFIX}_relations0\`
GROUP BY changeset
),
ways AS (
SELECT
  changeset,
  MIN(osm_timestamp) AS osm_timestamp_min,
  MAX(osm_timestamp) AS osm_timestamp_max,
  ARRAY_AGG(id) AS ways
FROM \`${PROJECT}.${DATASET}.${PREFIX}_ways0\`
GROUP BY changeset
),
nodes AS (
SELECT
  changeset,
  MIN(CAST(osm_timestamp/1000 AS INT64)) AS osm_timestamp_min,
  MAX(CAST(osm_timestamp/1000 AS INT64)) AS osm_timestamp_max,
  ARRAY_AGG(id) AS nodes
FROM \`${PROJECT}.${DATASET}.${PREFIX}_nodes0\`
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
  times.changeset AS id,
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
"
