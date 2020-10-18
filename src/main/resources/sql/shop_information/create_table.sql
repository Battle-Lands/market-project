CREATE TABLE IF NOT EXISTS shop_information (
    id int NOT NULL AUTO_INCREMENT,
    owner varchar(16) NOT NULL,
    location text DEFAULT NULL,
    description text DEFAULT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY id(id),
    PRIMARY KEY(owner)
);