CREATE TABLE IF NOT EXISTS shop_update_history (
    id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    shop_id int NOT NULL,
    type ENUM(
        "CREATED", "REMOVED",
        "BANNED", "UNBANNED",
        "UPDATED_LOCATION", "REMOVED_LOCATION",
        "UPDATED_DESCRIPTION", "REMOVED_DESCRIPTION"
    ) NOT NULL,
    old_value varchar(254),
    new_value varchar(254),
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT shop_update_ref
        FOREIGN KEY (shop_id)
        REFERENCES shop_information(id)
        ON DELETE CASCADE
);