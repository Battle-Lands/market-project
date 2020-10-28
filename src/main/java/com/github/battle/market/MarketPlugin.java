package com.github.battle.market;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.core.plugin.PluginCore;
import com.github.battle.market.command.ShopCommand;
import com.github.battle.market.expansion.ShopExpansion;
import com.github.battle.market.job.ShopBanQueue;
import com.github.battle.market.job.ShopUpdateQueue;
import com.github.battle.market.listener.ChestShopActionListener;
import com.github.battle.market.listener.PlayerShopEntityListener;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.manager.ShopBanManager;
import com.github.battle.market.manager.ShopEventManager;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.item.PlayerShopItemAdapter;
import com.github.battle.market.view.ShopView;

import java.util.concurrent.ForkJoinPool;

public final class MarketPlugin extends PluginCore {

    private ShopUpdateQueue shopUpdateQueue;
    private ShopBanQueue shopBanQueue;

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
            "shop_update.create_table",
            "shop_ban.create_table"
          );

        final PlayerShopManager playerShopManager = new PlayerShopManager(mysqlBootstrap);
        mysqlBootstrap.executeAsync(playerShopManager.getEntitySync());

        final ShopExpansion shopExpansion = new ShopExpansion(playerShopManager);
        shopExpansion.register();

        final PlayerShopItemAdapter playerShopItemAdapter = new PlayerShopItemAdapter(playerShopManager, this);
        final ShopView shopPaginatedView = new ShopView(this, playerShopItemAdapter);

        this.shopUpdateQueue = new ShopUpdateQueue(this, mysqlBootstrap);
        this.shopBanQueue = new ShopBanQueue(this, mysqlBootstrap);

        final ShopBanManager shopBanManager = new ShopBanManager(shopBanQueue, playerShopManager);
        final ShopEventManager shopEventManager = new ShopEventManager(shopUpdateQueue, shopBanManager);

        registerListeners(new PlayerShopEntityListener(shopBanManager), new ChestShopActionListener());
        registerListenerFromInventory(this);
        registerCommands(new ShopCommand(
          playerShopManager,
          shopEventManager,
          shopPaginatedView
        ));
    }

    @Override
    public void onPluginDisable() {
        shopUpdateQueue.run();
        shopBanQueue.run();

        mySQLRequester.close();
        mysqlBootstrap.closeForkJoinPool();
    }
}
