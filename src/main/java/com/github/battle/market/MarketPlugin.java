package com.github.battle.market;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.core.plugin.PluginCore;
import com.github.battle.market.command.ShopCommand;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.PlayerShopItemAdapter;
import com.github.battle.market.view.ShopView;
import lombok.Getter;

import java.util.concurrent.ForkJoinPool;

@Getter
public final class MarketPlugin extends PluginCore {

    private MySQLRequester mySQLRequester;
    private MysqlBootstrap mysqlBootstrap;

    @Override
    public void onPluginEnable() {
        this.mySQLRequester = new MySQLRequester(
          getCredentialRegistry()
            .getMysqlCredential()
        ).connect();

        this.mysqlBootstrap = new MysqlBootstrap(this, mySQLRequester, new ForkJoinPool(5))
          .createInitialTables(
            "shop_information.create_table",
            "shop_transaction.create_table"
          );

        final PlayerShopManager playerShopManager = new PlayerShopManager(
          mySQLRequester,
          mysqlBootstrap
        );

        mysqlBootstrap.executeAsync(playerShopManager.getEntitySync());

        final PlayerShopItemAdapter playerShopItemAdapter = new PlayerShopItemAdapter(playerShopManager);
        final ShopView shopPaginatedView = new ShopView(this, playerShopItemAdapter);

        registerListenerFromInventory(this);
        registerCommands(new ShopCommand(
          playerShopManager,
          shopPaginatedView
        ));
    }

    @Override
    public void onDisable() {
        mySQLRequester.close();
        mysqlBootstrap.closeForkJoinPool();
    }
}
