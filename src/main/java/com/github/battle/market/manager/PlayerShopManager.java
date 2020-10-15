package com.github.battle.market.manager;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.market.bootstrap.MysqlBootstrap;
import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.serializator.PlayerShopEntityAdapter;
import com.github.battle.market.serializator.PlayerShopEntitySerializer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public final class PlayerShopManager {

    private final LoadingCache<String, Optional<PlayerShopEntity>> loadingCache;
    private final PlayerShopEntitySerializer playerShopEntitySerializer;
    private final PlayerShopEntityAdapter playerShopEntityAdapter;

    public PlayerShopManager(@NonNull MySQLRequester requester, @NonNull MysqlBootstrap bootstrap) {
        this.playerShopEntitySerializer = new PlayerShopEntitySerializer(requester, bootstrap);
        this.playerShopEntityAdapter = new PlayerShopEntityAdapter();

        final PlayerShopCacheLoader loader = new PlayerShopCacheLoader(
          playerShopEntityAdapter,
          requester,
          bootstrap
        );

        this.loadingCache = CacheBuilder
          .newBuilder()
          .expireAfterWrite(1, TimeUnit.HOURS)
          .build(loader);

        loadingCache
          .asMap()
          .putAll(bootstrap.getAllPlayerShopEntities(playerShopEntityAdapter));
    }

    public void refleshPlayerShop(OfflinePlayer player) {
        final PlayerShopEntity playerShop = getPlayerShop(player);
        if (playerShop == null) return;

        playerShopEntitySerializer.serializeModel(playerShop);
        loadingCache.refresh(getName(player));
    }

    public int getPlayerShopSize() {
        return loadingCache.asMap().size();
    }

    public List<Optional<PlayerShopEntity>> getAllShopEntities() {
        return new ArrayList<>(loadingCache.asMap().values());
    }

    public PlayerShopEntity getPlayerShop(OfflinePlayer player) {
        if (!player.hasPlayedBefore()) return null;

        final Optional<PlayerShopEntity> unchecked = loadingCache.getUnchecked(getName(player));
        if (!unchecked.isPresent()) return null;

        return unchecked.get();
    }

    public String getName(OfflinePlayer player) {
        return player.getName().toLowerCase();
    }
}
