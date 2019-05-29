SELECT 
  mi.`mv_id` AS mvId,
  mi.`name` AS mvName,
  mi.`brief` AS brief,
  mi.`details` AS details,
  mi.`show_date` AS showDate,
  mi.`player` AS player,
  mi.`director` AS director,
  mi.`poster` AS poster,
  ms.`source_url` AS sourceUrl,
  wi.`website_id` AS websiteId,
  wi.`name` AS websiteName,
  wi.`address` AS address 
FROM
  t_mv_info mi 
  LEFT JOIN t_mv_source ms 
    ON mi.`mv_id` = ms.`mv_id` 
  LEFT JOIN t_website_info wi 
    ON wi.`website_id` = ms.`website_id` 
WHERE mi.`name` LIKE CONCAT('%',?,'%')
LIMIT ?,?