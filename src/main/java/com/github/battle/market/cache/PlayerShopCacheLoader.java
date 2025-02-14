package com.github.battle.market.cache;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.shop.PlayerShopEntityAdapter;
import com.google.common.cache.CacheLoader;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@SuppressWarnings("all")
@RequiredArgsConstructor
public final class PlayerShopCacheLoader extends CacheLoader<String, Optional<ShopEntity>> {

    private final PlayerShopEntityAdapter playerShopEntityAdapter;
    private final MysqlBootstrap bootstrap;

    @Override
    public Optional<ShopEntity> load(String player) throws Exception {
        return bootstrap.getRequester().result(
          bootstrap.getQuery("shop_information.get_by_owner"),
          playerShopEntityAdapter::adaptModel,
          player
        );
    }
}
