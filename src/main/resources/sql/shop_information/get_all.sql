SELECT
    shop_information.*, 
    SUM(IF(type="BUY", amount, 0)) as buyAmount,
    SUM(IF(type="SELL", amount, 0)) as sellAmount
FROM
    shop_information
LEFT JOIN shop_transaction_history as t
    ON (shop_information.id = t.shop_id) 
GROUP BY shop_information.id