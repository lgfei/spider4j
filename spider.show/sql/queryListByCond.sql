SELECT 
  * 
FROM
  (SELECT 
    mi.`mv_id` AS mvId,
    mi.`name` AS mvName,
    mi.`player` AS player,
    mi.`poster` AS poster,
    GROUP_CONCAT(DISTINCT wi.`website_id` ORDER BY wi.`website_id` DESC) AS wids 
  FROM
    t_mv_info mi 
    LEFT JOIN t_mv_source ms 
      ON mi.`mv_id` = ms.`mv_id` 
    LEFT JOIN t_website_info wi 
      ON ms.`website_id` = wi.`website_id` 
  WHERE mi.`show_date` BETWEEN  ? AND ? 
  GROUP BY mi.`mv_id`) t 
WHERE t.wids = ?
LIMIT ?,?