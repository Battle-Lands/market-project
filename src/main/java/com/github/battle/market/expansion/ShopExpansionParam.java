package com.github.battle.market.expansion;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.OfflinePlayer;

@Getter
@RequiredArgsConstructor
public abstract class ShopExpansionParam {

    private final @NonNull String name;
    private final PlayerShopManager playerShopManager;

    protected abstract String onRequest(OfflinePlayer player, @NonNull String params);

    public ShopEntity getPlayerShop(@NonNull OfflinePlayer offlinePlayer) {
        return playerShopManager.getCheckedPlayerShop(offlinePlayer);
    }
}
