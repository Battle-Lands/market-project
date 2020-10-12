package com.github.battle.market.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
@RequiredArgsConstructor
public final class PlayerShopEntity {

    private final String name;

    private String description;
    private Location location;
}
