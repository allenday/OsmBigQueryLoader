WITH relbox AS (
  SELECT relboxpoint.id,
    MIN(edge_left) AS edge_left,
    MIN(edge_bottom) AS edge_bottom,
    MAX(edge_right) AS edge_right,
    MAX(edge_top) AS edge_top
  FROM
  (
    SELECT 
      rel.id, 
      members.id AS m_id,
      ST_X(COALESCE(rel.bounding_box.top_left, way.bounding_box.top_left, node.geometry)) AS edge_left,
      ST_Y(COALESCE(rel.bounding_box.bottom_left, way.bounding_box.bottom_left, node.geometry)) AS edge_bottom,
      ST_X(COALESCE(rel.bounding_box.bottom_right, way.bounding_box.bottom_right, node.geometry)) AS edge_right,
      ST_Y(COALESCE(rel.bounding_box.top_right, way.bounding_box.top_right, node.geometry)) AS edge_top
    FROM 
      `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_relations1` AS rel JOIN UNNEST(members) AS members
      LEFT JOIN `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_nodes1` AS node ON (members.id = node.id)
      LEFT JOIN `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_ways1` AS way ON (members.id = way.id)
  ) AS relboxpoint
  GROUP BY relboxpoint.id
)

SELECT
  rel0.id,
  rel0.version,
  rel0.username,
  rel0.changeset,
  rel0.visible,
  TIMESTAMP_SECONDS(CAST(rel0.osm_timestamp/1000 AS INT64)) AS osm_timestamp, 
  NULL AS geometry,
  STRUCT(
      SAFE.ST_MAKEPOLYGON(SAFE.ST_MAKELINE([
      ST_GEOGPOINT(edge_left,edge_top),
      ST_GEOGPOINT(edge_left,edge_bottom),
      ST_GEOGPOINT(edge_right,edge_bottom),
      ST_GEOGPOINT(edge_right,edge_top)
      ])) AS bounding_box,
    ST_GEOGPOINT(edge_left,edge_top) AS top_left,
    ST_GEOGPOINT(edge_left,edge_bottom) AS bottom_left,
    ST_GEOGPOINT(edge_right,edge_bottom) AS bottom_right,
    ST_GEOGPOINT(edge_right,edge_top) AS top_right
  ) AS bounding_box,
  rel0.members,
  rel0.all_tags
FROM 
  relbox, 
  `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_relations0` AS rel0 JOIN UNNEST(members) as rel0members
WHERE 
  rel0.id != relbox.id
  AND rel0.id NOT IN (SELECT id FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_relations1` AS rel)
  AND rel0members.id = relbox.id
LIMIT 1000
