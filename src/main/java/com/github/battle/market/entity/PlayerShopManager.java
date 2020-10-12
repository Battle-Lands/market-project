package com.github.battle.market.entity;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public final class PlayerShopManager {

    @Getter
    private final List<PlayerShopEntity> playerShopEntities;

    public PlayerShopManager() {
        this.playerShopEntities = new ArrayList<>();
    }

    public PlayerShopEntity registerPlayerShop(PlayerShopEntity playerShopEntity) {
        playerShopEntities.add(playerShopEntity);
        return playerShopEntity;
    }

    public PlayerShopEntity getPlayerShop(Player player) {
        for (PlayerShopEntity playerShopEntity : playerShopEntities) {
            if (playerShopEntity.getName().equalsIgnoreCase(player.getName())) {
                return playerShopEntity;
            }
        }

        return null;
    }

    public PlayerShopEntity getOrCreatePlayerShop(Player player) {
        final PlayerShopEntity playerShop = getPlayerShop(player);
        if (playerShop != null) return playerShop;

        return registerPlayerShop(
          new PlayerShopEntity(player.getName())
        );
    }
}
