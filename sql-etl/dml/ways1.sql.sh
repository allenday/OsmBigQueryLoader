echo "
SELECT w.id, w.version, w.username, w.changeset, w.visible, w.osm_timestamp, t.nodes, w.geometry, 
  \`${PROJECT}.${DATASET}.get_extent\`(w.geometry) AS extent, t.all_tags FROM
(

--linestring
SELECT
  ways.id,
  ways.version,
  ways.username,
  ways.changeset,
  ways.visible,
  TIMESTAMP_SECONDS(CAST(ways.osm_timestamp/1000 AS INT64)) AS osm_timestamp,
  SAFE.ST_MAKELINE(ARRAY_AGG(nodes.geometry)) AS geometry
FROM
  \`${PROJECT}.${DATASET}.${PREFIX}_ways0\` AS ways JOIN UNNEST(nodes) AS waynode,
  \`${PROJECT}.${DATASET}.${PREFIX}_nodes1\` AS nodes
WHERE waynode = nodes.id AND NOT EXISTS(SELECT 1 FROM UNNEST(ways.all_tags) WHERE key = 'area' AND value = 'yes')
GROUP BY ways.id, ways.version, ways.username, ways.changeset, ways.visible, ways.osm_timestamp

UNION ALL

--polygon
SELECT
  ways.id,
  ways.version,
  ways.username,
  ways.changeset,
  ways.visible,
  TIMESTAMP_SECONDS(CAST(ways.osm_timestamp/1000 AS INT64)) AS osm_timestamp,
  COALESCE(SAFE.ST_MAKEPOLYGON(ST_MAKELINE(ARRAY_AGG(nodes.geometry))), SAFE.ST_MAKELINE(ARRAY_AGG(nodes.geometry))) AS geometry
FROM
  \`${PROJECT}.${DATASET}.${PREFIX}_ways0\` AS ways JOIN UNNEST(nodes) AS waynode,
  \`${PROJECT}.${DATASET}.${PREFIX}_nodes1\` AS nodes
WHERE waynode = nodes.id AND EXISTS(SELECT 1 FROM UNNEST(ways.all_tags) WHERE key = 'area' AND value = 'yes')
GROUP BY ways.id, ways.version, ways.username, ways.changeset, ways.visible, ways.osm_timestamp
)
AS w, \`${PROJECT}.${DATASET}.${PREFIX}_ways0\` AS t
WHERE w.id = t.id
"
