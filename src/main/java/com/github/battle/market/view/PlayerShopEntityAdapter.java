package com.github.battle.market.view;

import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.entity.PlayerShopManager;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public final class PlayerShopEntityAdapter {

    private final PlayerShopManager playerShopManager;

    public List<PlayerShopEntityItem> adaptPlayerShopItemEntity() {
        final List<PlayerShopEntityItem> playerShopEntityItems = new ArrayList<>();
        for (PlayerShopEntity playerShopEntity : playerShopManager.getPlayerShopEntities()) {
            playerShopEntityItems.add(new PlayerShopEntityItem(playerShopEntity));
        }

        return playerShopEntityItems;
    }
}
