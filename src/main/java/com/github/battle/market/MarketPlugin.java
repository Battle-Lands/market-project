package com.github.battle.market;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.core.plugin.PluginCore;
import com.github.battle.market.command.ShopCommand;
import com.github.battle.market.expansion.ShopExpansion;
import com.github.battle.market.job.ShopUpdateQueue;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.manager.ShopEventManager;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.PlayerShopItemAdapter;
import com.github.battle.market.view.ShopView;

import java.util.concurrent.ForkJoinPool;

public final class MarketPlugin extends PluginCore {

    private ShopUpdateQueue shopUpdateQueue;
    private MySQLRequester mySQLRequester;
    private MysqlBootstrap mysqlBootstrap;

    @Override
    public void onPluginEnable() {
        saveDefaultConfig();

        this.mySQLRequester = new MySQLRequester(
          getCredentialRegistry()
            .getMysqlCredential()
        ).connect();

        this.mysqlBootstrap = new MysqlBootstrap(this, mySQLRequester, new ForkJoinPool(5))
          .createInitialTables(
            "shop_information.create_table",
            "shop_transaction.create_table",
            "shop_update.create_table"
          );

        final PlayerShopManager playerShopManager = new PlayerShopManager(
          mySQLRequester,
          mysqlBootstrap
        );

        mysqlBootstrap.executeAsync(playerShopManager.getEntitySync());

        final ShopExpansion shopExpansion = new ShopExpansion(playerShopManager);
        shopExpansion.register();

        final PlayerShopItemAdapter playerShopItemAdapter = new PlayerShopItemAdapter(playerShopManager, this);
        final ShopView shopPaginatedView = new ShopView(template, playerShopManager);

        this.shopUpdateQueue = new ShopUpdateQueue(this, mysqlBootstrap, mySQLRequester);
        final ShopEventManager shopEventManager = new ShopEventManager(shopUpdateQueue);

        registerListenerFromInventory(this);
        registerCommands(new ShopCommand(
          playerShopManager,
          shopPaginatedView,
          shopEventManager
        ));
    }

    @Override
    public void onPluginDisable() {
        mysqlBootstrap.executeAsync(shopUpdateQueue);
        mySQLRequester.close();
        mysqlBootstrap.closeForkJoinPool();
    }
}
