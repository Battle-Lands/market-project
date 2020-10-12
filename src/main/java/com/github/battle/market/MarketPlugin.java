package com.github.battle.market;

import com.github.battle.core.plugin.PluginCore;
import com.github.battle.market.command.ShopCommand;
import com.github.battle.market.entity.PlayerShopManager;
import com.github.battle.market.event.PlayerInfoSell;
import com.github.battle.market.view.PlayerShopEntityAdapter;
import com.github.battle.market.view.ShopPaginatedView;

import java.math.BigDecimal;

public final class MarketPlugin extends PluginCore {

    @Override
    public void onPluginEnable() {
        final PlayerShopManager playerShopManager = new PlayerShopManager();
        final PlayerShopEntityAdapter playerShopEntityAdapter = new PlayerShopEntityAdapter(playerShopManager);
        final ShopPaginatedView shopPaginatedView = new ShopPaginatedView(this, playerShopEntityAdapter);

        registerListeners(new PlayerInfoSell());
        registerCommands(new ShopCommand(
          playerShopManager,
          shopPaginatedView
        ));

        registerListenerFromInventory(this);
    }
}
