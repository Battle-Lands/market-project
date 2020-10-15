package com.github.battle.market;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.core.plugin.PluginCore;
import com.github.battle.market.bootstrap.MysqlBootstrap;
import com.github.battle.market.command.ShopCommand;
import com.github.battle.market.event.PlayerInfoSell;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.serializator.PlayerShopItemAdapter;
import com.github.battle.market.view.ShopView;
import lombok.Getter;

@Getter
public final class MarketPlugin extends PluginCore {

    private MySQLRequester mySQLRequester;

    @Override
    public void onPluginEnable() {
        saveDefaultConfig();

        this.mySQLRequester = new MySQLRequester(
          getCredentialRegistry().getMysqlCredential()
        ).connect();

        final MysqlBootstrap mysqlBootstrap = new MysqlBootstrap(
          getConfig(),
          mySQLRequester
        ).createInitialTables();

        final PlayerShopManager playerShopManager = new PlayerShopManager(
          mySQLRequester,
          mysqlBootstrap
        );

        final PlayerShopItemAdapter playerShopItemAdapter = new PlayerShopItemAdapter(playerShopManager);
        final ShopView shopPaginatedView = new ShopView(this, playerShopItemAdapter);

        registerListenerFromInventory(this);
        registerListeners(new PlayerInfoSell());
        registerCommands(new ShopCommand(
          playerShopManager,
          shopPaginatedView
        ));
    }

    @Override
    public void onDisable() {
        mySQLRequester.close();
    }
}
