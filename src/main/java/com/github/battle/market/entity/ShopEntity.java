package com.github.battle.market.entity;

import org.bukkit.Bukkit;
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

    void setCreated(boolean created);

    boolean isCreated();

    void setState(ShopState state);

    ShopState getState();

    long getSellAmount();

    void setSellAmount(long sellAmount);

    long getBuyAmount();

    void setBuyAmount(long buyAmount);

    default boolean hasLocationSet() {
        return getLocation() != null;
    }

    default boolean hasDescriptionSet() {
        return getDescription() != null;
    }

    default boolean isAccessible() {
        return isCreated() || getState().isAccessible();
    }

    default long getTotalAmount() {
        return getBuyAmount() + getSellAmount();
    }

    default Optional<ShopEntity> optional() {
        return Optional.of(this);
    }

    default OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(getOwner());
    }
}
