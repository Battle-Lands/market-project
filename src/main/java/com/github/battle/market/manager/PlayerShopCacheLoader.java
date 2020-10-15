package com.github.battle.market.manager;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.market.bootstrap.MysqlBootstrap;
import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.serializator.PlayerShopEntityAdapter;
import com.google.common.cache.CacheLoader;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@SuppressWarnings("all")
@RequiredArgsConstructor
public final class PlayerShopCacheLoader extends CacheLoader<String, Optional<PlayerShopEntity>> {

    private final PlayerShopEntityAdapter playerShopEntityAdapter;
    private final MySQLRequester requester;
    private final MysqlBootstrap bootstrap;

    @Override
    public Optional<PlayerShopEntity> load(String player) throws Exception {
        return requester.result(
          bootstrap.getQuery("get_shop_information"),
          playerShopEntityAdapter::adaptModel,
          player
        );
    }
}
