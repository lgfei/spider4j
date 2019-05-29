SELECT 
  mi.`mv_id` AS mvId,
  mi.`name` AS mvName,
  mi.`player` AS player,
  mi.`poster` AS poster 
FROM
  t_mv_info mi 
ORDER BY mi.`updated_time` DESC,mv_id DESC
LIMIT 8 