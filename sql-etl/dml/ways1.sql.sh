echo "
--shard $W
SELECT 
  ways.id AS id,
  ways.version AS version,
  SAFE.ST_MAKELINE(ARRAY_AGG(nodes.geometry)) AS geometry
FROM \`${PROJECT}.${DATASET}.${PREFIX}_ways0\` AS ways JOIN UNNEST(ways.nodes) AS waynodes
JOIN \`${PROJECT}.${DATASET}.${PREFIX}_nodes1\` AS nodes ON (waynodes = nodes.id)
WHERE ARRAY_LENGTH(ways.nodes) >= ($W)*100 AND ARRAY_LENGTH(ways.nodes) < ($W+1)*100
  AND ways.changeset >= nodes.changeset
GROUP BY ways.id, ways.version, nodes.version
HAVING nodes.version = MAX(nodes.version)
"

#HAVING COUNT(nodes.geometry) >= 1900 AND COUNT(nodes.geometry) < 2000
#WITH nc AS (
#  SELECT CONCAT(CAST(ways.id AS STRING), '.', CAST(ways.version AS STRING)) AS vid, ARRAY_LENGTH(ARRAY_AGG(nodes.geometry)) AS len
#  FROM \`${PROJECT}.${DATASET}.${PREFIX}_ways0\` AS ways JOIN UNNEST(ways.nodes) AS waynodes
#  JOIN \`${PROJECT}.${DATASET}.${PREFIX}_nodes1\` AS nodes ON (waynodes = nodes.id AND ways.version = nodes.version)
#  GROUP BY ways.id, ways.version 
#)
#
#SELECT ways.id,ways.version,SAFE.ST_MAKELINE(ARRAY_AGG(nodes.geometry)) AS geometry
#FROM \`${PROJECT}.${DATASET}.${PREFIX}_ways0\` AS ways JOIN UNNEST(ways.nodes) AS waynodes
#JOIN \`${PROJECT}.${DATASET}.${PREFIX}_nodes1\` AS nodes ON (waynodes = nodes.id AND ways.version = nodes.version)
#WHERE
#  CONCAT(CAST(ways.id AS STRING), '.', CAST(ways.version AS STRING)) NOT IN (SELECT vid FROM nc WHERE len > 2000)
#GROUP BY ways.id, ways.version 
