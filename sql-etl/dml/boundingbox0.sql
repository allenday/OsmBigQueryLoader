-- ,boundingbox AS (
  SELECT
    rel.id,
    rel.version,
    rel.username,
    rel.changeset,
    rel.visible,
    TIMESTAMP_SECONDS(CAST(rel.osm_timestamp AS INT64)) AS osm_timestamp,
    STRUCT(
      COALESCE(
        SAFE.ST_MAKEPOLYGON(SAFE.ST_MAKELINE([
          ST_GEOGPOINT(MIN(ST_X(p)),MAX(ST_Y(p))),
          ST_GEOGPOINT(MIN(ST_X(p)),MIN(ST_Y(p))),
          ST_GEOGPOINT(MAX(ST_X(p)),MIN(ST_Y(p))),
          ST_GEOGPOINT(MAX(ST_X(p)),MAX(ST_Y(p)))
        ])), 
        ST_GEOGPOINT(MIN(ST_X(p)),MAX(ST_Y(p)))
      ) AS geometry,
      ST_GEOGPOINT(MIN(ST_X(p)),MAX(ST_Y(p))) AS top_left,
      ST_GEOGPOINT(MIN(ST_X(p)),MIN(ST_Y(p))) AS bottom_left,
      ST_GEOGPOINT(MAX(ST_X(p)),MIN(ST_Y(p))) AS bottom_right,
      ST_GEOGPOINT(MAX(ST_X(p)),MAX(ST_Y(p))) AS top_right
    ) AS boundingbox
  FROM 
    (
      SELECT id, nodes.geometry AS p FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_nodes1` AS nodes
      UNION ALL
      SELECT id, ways.boundingbox.top_left AS p FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_ways1` AS ways
      UNION ALL
      SELECT id, ways.boundingbox.bottom_left AS p FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_ways1` AS ways
      UNION ALL
      SELECT id, ways.boundingbox.bottom_right AS p FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_ways1` AS ways
      UNION ALL
      SELECT id, ways.boundingbox.top_right AS p FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_ways1` AS ways
    ) AS points,
    `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_relations0` AS rel
  WHERE points.id IN (
    SELECT DISTINCT rel.id FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_relations0` AS rel
    JOIN UNNEST(members) AS mem WHERE mem.type != 'RELATION' AND rel.id NOT IN (
      SELECT rel.id FROM `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_relations0` 
      JOIN UNNEST(members) AS mem WHERE mem.type = 'RELATION'
    )
  ) 

  GROUP BY id, version, username, changeset, visible, osm_timestamp

-- )

-- SELECT 
--   boundingbox.id,
--   boundingbox.version,
--   boundingbox.username,
--   boundingbox.changeset,
--   boundingbox.visible,
--   boundingbox.osm_timestamp,
-- --  geometry,
--   boundingbox.boundingbox,
--   relations0.members,
--   relations0.all_tags
-- FROM
--   boundingbox,
--   `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_relations0` AS relations0 JOIN UNNEST(members) AS members
-- --   `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_ways1` AS ways1,
-- --   `openstreetmap-public-data-dev.osmbigqueryloader_dev.planet_nodes1` AS nodes1
-- --   -- we need to join members against ways1 and nodes1 IN ORDER and then do a ST_UNION() to build the collection geometry

-- SELECT leaf.id AS leaf, boundingbox.* FROM leaf, boundingbox
-- WHERE leaf.id = boundingbox.id

--ORDER BY id
