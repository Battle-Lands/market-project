CREATE TABLE IF NOT EXISTS shop_ban_history (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	shop_id int NOT NULL,
    staff varchar(16) NOT NULL,
    reason text NOT NULL,
    type ENUM("BANNED", "UNBANNED") NOT NULL,
    CONSTRAINT shop_ban_ref
        FOREIGN KEY (shop_id)
        REFERENCES shop_information(id)
        ON DELETE CASCADE
);