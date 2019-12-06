SELECT w.id, w.version, w.username, w.changeset, w.visible, w.osm_timestamp, t.nodes, w.geometry, w.bounding_box, t.all_tags FROM (

--linestring
SELECT
  ways.id,
  ways.version,
  ways.username,
  ways.changeset,
  ways.visible,
  TIMESTAMP_SECONDS(CAST(ways.osm_timestamp/1000 AS INT64)) AS osm_timestamp,
  COALESCE(SAFE.ST_MAKEPOLYGON(ST_MAKELINE(ARRAY_AGG(nodes.geometry))), SAFE.ST_MAKELINE(ARRAY_AGG(nodes.geometry))) AS geometry,
  STRUCT(
    SAFE.ST_MAKELINE([
      ST_GEOGPOINT(MIN(ST_X(nodes.geometry)),MAX(ST_Y(nodes.geometry))),
      ST_GEOGPOINT(MIN(ST_X(nodes.geometry)),MIN(ST_Y(nodes.geometry))),
      ST_GEOGPOINT(MAX(ST_X(nodes.geometry)),MIN(ST_Y(nodes.geometry))),
      ST_GEOGPOINT(MAX(ST_X(nodes.geometry)),MAX(ST_Y(nodes.geometry)))
    ]) AS geometry,
    ST_GEOGPOINT(MIN(ST_X(nodes.geometry)),MAX(ST_Y(nodes.geometry))) AS top_left,
    ST_GEOGPOINT(MIN(ST_X(nodes.geometry)),MIN(ST_Y(nodes.geometry))) AS bottom_left,
    ST_GEOGPOINT(MAX(ST_X(nodes.geometry)),MIN(ST_Y(nodes.geometry))) AS bottom_right,
    ST_GEOGPOINT(MAX(ST_X(nodes.geometry)),MAX(ST_Y(nodes.geometry))) AS top_right
  ) AS bounding_box
FROM
  `osmbigqueryloader_dev.planet_ways0` AS ways JOIN UNNEST(nodes) AS waynode,
  `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_nodes1` AS nodes
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
  COALESCE(SAFE.ST_MAKEPOLYGON(ST_MAKELINE(ARRAY_AGG(nodes.geometry))), SAFE.ST_MAKELINE(ARRAY_AGG(nodes.geometry))) AS geometry,
  STRUCT(
    SAFE.ST_MAKEPOLYGON(SAFE.ST_MAKELINE([
      ST_GEOGPOINT(MIN(ST_X(nodes.geometry)),MAX(ST_Y(nodes.geometry))),
      ST_GEOGPOINT(MIN(ST_X(nodes.geometry)),MIN(ST_Y(nodes.geometry))),
      ST_GEOGPOINT(MAX(ST_X(nodes.geometry)),MIN(ST_Y(nodes.geometry))),
      ST_GEOGPOINT(MAX(ST_X(nodes.geometry)),MAX(ST_Y(nodes.geometry)))
    ])) AS geometry,
    ST_GEOGPOINT(MIN(ST_X(nodes.geometry)),MAX(ST_Y(nodes.geometry))) AS top_left,
    ST_GEOGPOINT(MIN(ST_X(nodes.geometry)),MIN(ST_Y(nodes.geometry))) AS bottom_left,
    ST_GEOGPOINT(MAX(ST_X(nodes.geometry)),MIN(ST_Y(nodes.geometry))) AS bottom_right,
    ST_GEOGPOINT(MAX(ST_X(nodes.geometry)),MAX(ST_Y(nodes.geometry))) AS top_right
  ) AS bounding_box
FROM
  `osmbigqueryloader_dev.planet_ways0` AS ways JOIN UNNEST(nodes) AS waynode,
  `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_nodes1` AS nodes
WHERE waynode = nodes.id AND EXISTS(SELECT 1 FROM UNNEST(ways.all_tags) WHERE key = 'area' AND value = 'yes')
GROUP BY ways.id, ways.version, ways.username, ways.changeset, ways.visible, ways.osm_timestamp
)
AS w, `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_ways0` AS t
WHERE w.id = t.id
