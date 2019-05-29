SELECT 
  mi.`mv_id` AS mvId,
  mi.`name` AS mvName,
  mi.`player` AS player,
  mi.`poster` AS poster
FROM
  t_mv_info mi 
ORDER BY mi.`hot_degree` DESC,mi.`updated_time` DESC
LIMIT 8 