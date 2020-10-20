package com.github.battle.market.cache;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@SuppressWarnings("all")
public final class PlayerShopRemovalListener implements RemovalListener<String, Optional<ShopEntity>> {

    private final MySQLRequester requester;
    private final MysqlBootstrap bootstrap;

    @Override
    public void onRemoval(RemovalNotification<String, Optional<ShopEntity>> notification) {
        if (notification.getCause() != RemovalCause.EXPLICIT) return;
        bootstrap.executeAsync(() -> {
            requester.executeUpdate(
              bootstrap.getQuery("shop_information.remove_by_owner"),
              notification.getKey()
            );
        });
    }
}
