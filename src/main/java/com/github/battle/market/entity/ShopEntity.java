package com.github.battle.market.entity;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.math.BigInteger;
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

    void setCreated(boolean created);

    ShopState getState();

    void setState(ShopState state);

    ShopState getRawState();

    OfflinePlayer getPlayer();

    long getSellAmount();

    void setSellAmount(long amount);

    void addSellAmount(long amount);

    long getBuyAmount();

    void setBuyAmount(long amount);

    void addBuyAmount(long amount);

    default boolean hasLocationSet() {
        return getLocation() != null;
    }

    default boolean hasDescriptionSet() {
        return getDescription() != null;
    }

    default boolean isAccessible() {
        return getState().isAccessible() && !getPlayer().isBanned();
    }

    default boolean canInitialize() {
        return getState().isCanInitialize();
    }

    default long getTotalAmount() {
        return getBuyAmount() + getSellAmount();
    }

    default Optional<ShopEntity> optional() {
        return Optional.of(this);
    }

    default boolean isBanned() {
        return getState() == ShopState.BANNED;
    }
}
