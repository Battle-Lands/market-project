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

    boolean hasDescriptionSet();

    Location getLocation();

    void setLocation(Location location);

    boolean hasLocationSet();

    void setCreated(boolean created);

    boolean isCreated();

    void setState(ShopState state);

    ShopState getState();

    boolean isAccessible();

    Optional<ShopEntity> optional();

    OfflinePlayer getPlayer();

    long getSellAmount();

    void setSellAmount(long sellAmount);

    long getBuyAmount();

    void setBuyAmount(long buyAmount);

    long getTotalAmount();
}
