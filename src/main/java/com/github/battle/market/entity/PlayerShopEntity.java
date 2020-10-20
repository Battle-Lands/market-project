package com.github.battle.market.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.sql.Timestamp;
import java.util.Optional;

@Data
@Builder
@ToString
public final class PlayerShopEntity implements ShopEntity {

    private final int id;
    private final String owner;
    private final boolean created;
    private final Timestamp createdAt;

    private String description;
    private Location location;

    public Optional<ShopEntity> optional() {
        return Optional.of(this);
    }

    public OfflinePlayer getPlayer() {
        return Bukkit.getOfflinePlayer(owner);
    }
}
