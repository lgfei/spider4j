SELECT 
  *
FROM
  (SELECT 
    GROUP_CONCAT(DISTINCT wi.`website_id` ORDER BY wi.`website_id` DESC) AS wids,
    GROUP_CONCAT(DISTINCT wi.`name` ORDER BY wi.`website_id` DESC) wnames 
  FROM
    t_mv_source ms 
    LEFT JOIN t_website_info wi 
      ON wi.`website_id` = ms.`website_id` 
  GROUP BY ms.`mv_id`) t 
GROUP BY t.wids,t.wnames