package com.github.battle.market.expansion;

import com.github.battle.market.expansion.param.ShopCustomMessageParam;
import com.github.battle.market.expansion.param.ShopTransactionParam;
import com.github.battle.market.manager.PlayerShopManager;
import lombok.NonNull;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public final class ShopExpansion extends PlaceholderExpansion {

    private final PlayerShopManager playerShopManager;
    private final ShopExpansionParam[] expansionParams;

    public ShopExpansion(@NonNull PlayerShopManager playerShopManager) {
        this.playerShopManager = playerShopManager;
        this.expansionParams = getExpansionParams();
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        final ShopExpansionParam expansionParam = getShopExpansionParamByName(params);
        if (expansionParam == null) return null;

        return expansionParam.onRequest(
          player,
          params
        );
    }

    public ShopExpansionParam getShopExpansionParamByName(@NonNull String params) {
        for (ShopExpansionParam expansionParam : expansionParams) {
            if (params.startsWith(expansionParam.getName())) {
                return expansionParam;
            }
        }
        return null;
    }

    private ShopExpansionParam[] getExpansionParams() {
        return new ShopExpansionParam[]{
          new ShopTransactionParam(playerShopManager),
          new ShopCustomMessageParam(playerShopManager)
        };
    }

    @Override
    public @NotNull String getIdentifier() {
        return "battleshop";
    }

    @Override
    public @NotNull String getAuthor() {
        return "yzkingboos";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0-SNAPSHOT";
    }
}
