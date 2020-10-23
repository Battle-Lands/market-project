CREATE TABLE IF NOT EXISTS shop_information (
    id int NOT NULL AUTO_INCREMENT,
    owner varchar(16) NOT NULL,
    location varchar(150) DEFAULT NULL,
    description text DEFAULT NULL,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    state ENUM("ACCESSIBLE", "REMOVED", "BANNED") NOT NULL DEFAULT "ACCESSIBLE",
    KEY id(id),
    PRIMARY KEY(owner)
);