CREATE TABLE IF NOT EXISTS shop_transaction (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    shop_id int NOT NULL,
    dummy varchar(16) NOT NULL,
    price bigint NOT NULL,
    type ENUM("BUY", "SELL") NOT NULL,
    item_data text NOT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT shop_transaction_ref
        FOREIGN KEY (shop_id)
        REFERENCES shop_information(id)
        ON DELETE CASCADE
);