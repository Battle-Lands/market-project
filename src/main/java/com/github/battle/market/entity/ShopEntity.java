package com.github.battle.market.entity;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.sql.Timestamp;
import java.util.Optional;

public interface ShopEntity {

    int getId();
    String getOwner();
    Timestamp getCreatedAt();

    String getDescription();
    Location getLocation();

    Optional<ShopEntity> optional();
    OfflinePlayer getPlayer();

    void setLocation(Location location);
    void setDescription(String description);
}
