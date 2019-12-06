WITH relbox AS (
  SELECT relboxpoint.id,
    COALESCE(
      SAFE.ST_MAKEPOLYGON(SAFE.ST_MAKELINE([
        ST_GEOGPOINT(MIN(edge_left),MAX(edge_top)),
        ST_GEOGPOINT(MIN(edge_left),MAX(edge_bottom)),
        ST_GEOGPOINT(MIN(edge_right),MAX(edge_bottom)),
        ST_GEOGPOINT(MIN(edge_right),MAX(edge_top))
      ])),
      ST_GEOGPOINT(MIN(edge_left),MAX(edge_top))
    ) AS bounding_box,
    ST_GEOGPOINT(MIN(edge_left),MAX(edge_top)) AS top_left,
    ST_GEOGPOINT(MIN(edge_left),MAX(edge_bottom)) AS bottom_left,
    ST_GEOGPOINT(MIN(edge_right),MAX(edge_bottom)) AS bottom_right,
    ST_GEOGPOINT(MIN(edge_right),MAX(edge_top)) AS top_right
  FROM
  (
    SELECT rel.id, ROW_NUMBER() OVER(PARTITION BY rel.id) AS m_idx , members.id AS m_id,
      ST_X(COALESCE(way.bounding_box.top_left, nod.geometry)) AS edge_left,
      ST_Y(COALESCE(way.bounding_box.bottom_left, nod.geometry)) AS edge_bottom,
      ST_X(COALESCE(way.bounding_box.bottom_right, nod.geometry)) AS edge_right,
      ST_Y(COALESCE(way.bounding_box.top_right, nod.geometry)) AS edge_top
    FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_relations0` AS rel JOIN UNNEST(members) AS members
    LEFT JOIN `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_nodes1` AS nod ON (members.id = nod.id)
    LEFT JOIN `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_ways1` AS way ON (members.id = way.id)
  ) AS relboxpoint
  GROUP BY relboxpoint.id
)

SELECT
  rel.id,
  rel.version,
  rel.username,
  rel.changeset,
  rel.visible,
  TIMESTAMP_SECONDS(CAST(rel.osm_timestamp/1000 AS INT64)) AS osm_timestamp, 
  STRUCT(
    relbox.bounding_box,
    relbox.top_left,
    relbox.bottom_left,
    relbox.bottom_right,
    relbox.top_right
  ) AS bounding_box,
  rel.members,
  rel.all_tags
FROM 
  relbox, 
  `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_relations0` AS rel
WHERE 
  rel.id = relbox.id
AND rel.id IN (
  SELECT DISTINCT rel.id FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_relations0` AS rel JOIN UNNEST(members) AS mem WHERE mem.type != 'RELATION' AND rel.id NOT IN (
    SELECT rel.id FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_relations0` AS rel JOIN UNNEST(members) AS mem WHERE mem.type = 'RELATION'
  )
)
