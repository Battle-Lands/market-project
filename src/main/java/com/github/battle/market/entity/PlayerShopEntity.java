package com.github.battle.market.entity;

import com.github.battle.core.plugin.PluginCore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.sql.Timestamp;

@Data
@Builder
@ToString
public final class PlayerShopEntity implements ShopEntity {

    private final int id;
    private final String owner;
    private final Timestamp createdAt;

    private boolean created;
    private ShopState state;
    private String description;
    private Location location;
    private OfflinePlayer offlinePlayer;

    private long buyAmount;
    private long sellAmount;

    @Override
    public ShopState getState() {
        return state != null ? state : ShopState.ACCESSIBLE;
    }

    @Override
    public ShopState getRawState() {
        return state;
    }

    @Override
    public OfflinePlayer getPlayer() {
        return offlinePlayer != null
          ? offlinePlayer
          : (offlinePlayer = PluginCore.getOfflinePlayer(owner));
    }
}
