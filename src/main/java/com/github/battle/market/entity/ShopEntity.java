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

    void setDescription(String description);

    Location getLocation();

    void setLocation(Location location);

    boolean isCreated();

    Optional<ShopEntity> optional();

    OfflinePlayer getPlayer();
}
