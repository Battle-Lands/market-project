package com.github.battle.market.adapter;

import com.github.battle.core.common.ModelAdapter;
import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.view.PlayerShopItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public final class PlayerShopItemAdapter implements ModelAdapter<List<PlayerShopItem>, Void> {

    private final PlayerShopManager playerShopManager;

    @Override
    public List<PlayerShopItem> adaptModel(Void instance) {
        final List<PlayerShopItem> playerShopItemAdapters = new ArrayList<>();
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            final PlayerShopEntity playerShop = playerShopManager.getPlayerShop(offlinePlayer);
            if(playerShop == null) continue;
            playerShopItemAdapters.add(new PlayerShopItem(playerShop));
        }

        return playerShopItemAdapters;
    }
}
