package com.github.battle.market.expansion.param;

import com.github.battle.market.expansion.ShopExpansionParam;
import com.github.battle.market.manager.PlayerShopManager;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;

public final class ShopTransactionParam extends ShopExpansionParam {

    public ShopTransactionParam(@NonNull PlayerShopManager playerShopManager) {
        super("transaction_amount", playerShopManager);
    }

    @Override
    protected String onRequest(OfflinePlayer player, @NonNull String params) {
        return null;
    }
}
