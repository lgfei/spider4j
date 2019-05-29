SELECT 
  COUNT(1) AS total
FROM
  t_mv_info mi 
  LEFT JOIN t_mv_source ms 
    ON mi.`mv_id` = ms.`mv_id` 
  LEFT JOIN t_website_info wi 
    ON wi.`website_id` = ms.`website_id` 
WHERE mi.`name` LIKE CONCAT('%',?,'%')