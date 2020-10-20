package com.github.battle.market.expansion.param;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.expansion.ShopExpansionParam;
import com.github.battle.market.manager.PlayerShopManager;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;

public final class ShopCustomMessageParam extends ShopExpansionParam {

    public ShopCustomMessageParam(@NonNull PlayerShopManager playerShopManager) {
        super("custom_message", playerShopManager);
    }

    @Override
    protected String onRequest(OfflinePlayer player, @NonNull String params) {
        final ShopEntity shopEntity = getPlayerShopManager().getPlayerShop(player);
        if (shopEntity == null) return null;

        final String description = shopEntity.getDescription();
        if(description == null) return null;

        if (params.endsWith("_short")) {
            return description.length() > 57
              ? description.substring(0, 57).concat("...")
              : description;
        }

        return description;
    }
}
