package com.github.battle.market.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.bukkit.Location;

import java.sql.Timestamp;
import java.util.Optional;

@Data
@Builder
@ToString
public final class PlayerShopEntity {

    private final int id;
    private final String owner;
    private final Timestamp createdAt;

    private String description;
    private Location location;

    public Optional<PlayerShopEntity> optional() {
        return Optional.of(this);
    }
}
