package com.github.battle.market.manager;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.market.adapter.PlayerShopEntityAdapter;
import com.github.battle.market.bootstrap.MysqlBootstrap;
import com.github.battle.market.entity.PlayerShopEntity;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public final class PlayerShopManager {

    private final LoadingCache<String, Optional<PlayerShopEntity>> loadingCache;
    public PlayerShopManager(@NonNull PlayerShopEntityAdapter playerShopEntityAdapter, @NonNull MySQLRequester requester, @NonNull MysqlBootstrap bootstrap) {
        final PlayerShopCacheLoader loader = new PlayerShopCacheLoader(
          playerShopEntityAdapter,
          requester,
          bootstrap
        );

        this.loadingCache = CacheBuilder
          .newBuilder()
          .expireAfterWrite(1, TimeUnit.HOURS)
          .build(loader);
    }

    public void refleshPlayerShop(OfflinePlayer player) {
        if(!player.hasPlayedBefore()) return;
        loadingCache.refresh(player.getName());
    }

    public PlayerShopEntity getPlayerShop(OfflinePlayer player) {
        if(!player.hasPlayedBefore()) return null;

        final Optional<PlayerShopEntity> unchecked = loadingCache.getUnchecked(player.getName());
        if(!unchecked.isPresent()) return null;

        return unchecked.get();
    }
}
