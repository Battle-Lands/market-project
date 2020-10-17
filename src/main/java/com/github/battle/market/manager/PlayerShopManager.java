package com.github.battle.market.manager;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.market.cache.PlayerShopCacheLoader;
import com.github.battle.market.cache.PlayerShopRemovalListener;
import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.job.ShopEntitySync;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.PlayerShopEntityAdapter;
import com.github.battle.market.serializator.PlayerShopEntitySerializer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("all")
public final class PlayerShopManager {

    private final LoadingCache<String, Optional<PlayerShopEntity>> playerShopCache;
    private final PlayerShopEntitySerializer playerShopEntitySerializer;
    private final PlayerShopEntityAdapter playerShopEntityAdapter;
    private final MySQLRequester requester;
    private final MysqlBootstrap bootstrap;

    @Getter
    private final ShopEntitySync entitySync;

    public PlayerShopManager(@NonNull MySQLRequester requester, @NonNull MysqlBootstrap bootstrap) {
        this.playerShopEntitySerializer = new PlayerShopEntitySerializer(requester, bootstrap);
        this.playerShopEntityAdapter = new PlayerShopEntityAdapter();
        this.requester = requester;
        this.bootstrap = bootstrap;

        final PlayerShopCacheLoader loader = new PlayerShopCacheLoader(
          playerShopEntityAdapter,
          requester,
          bootstrap
        );

        this.playerShopCache = CacheBuilder
          .newBuilder()
          .removalListener(new PlayerShopRemovalListener(
            requester,
            bootstrap
          )).build(loader);

        this.entitySync = new ShopEntitySync(
          playerShopEntityAdapter,
          this,
          requester,
          bootstrap
        );
    }

    public void putAllEntities(Map<String, Optional<PlayerShopEntity>> optionalMap) {
        asShopMap().putAll(optionalMap);
    }

    public void refleshPlayerShop(OfflinePlayer player) {
        final PlayerShopEntity playerShop = getPlayerShop(player);
        if (playerShop == null) return;

        playerShopEntitySerializer.serializeModel(playerShop);
        if (playerShop.getId() == 0) {
            playerShopCache.refresh(getName(player));
        }
    }

    public boolean hasPlayerShop(OfflinePlayer player) {
        return asShopMap().containsKey(getName(player));
    }

    public void invalidPlayerShop(OfflinePlayer player) {
        playerShopCache.invalidate(getName(player));
    }

    public void updatePlayerShop(OfflinePlayer player, @NonNull PlayerShopEntity playerShopEntity) {
        if (!player.hasPlayedBefore()) return;
        playerShopCache.put(
          getName(player),
          playerShopEntity.optional()
        );
    }

    public PlayerShopEntity getEmptyShopEntity(OfflinePlayer player) {
        if (!player.hasPlayedBefore()) return null;
        return PlayerShopEntity
          .builder()
          .owner(getName(player))
          .build();
    }

    public int getPlayerShopSize() {
        return asShopMap().size();
    }

    public List<Optional<PlayerShopEntity>> getAllShopEntities() {
        return new ArrayList<>(asShopMap().values());
    }

    public PlayerShopEntity getLazyPlayerShop(OfflinePlayer player) {
        PlayerShopEntity playerShop = getPlayerShop(player);
        if (playerShop != null) return playerShop;

        playerShop = getEmptyShopEntity(player);
        updatePlayerShop(player, playerShop);
        return playerShop;
    }

    public PlayerShopEntity getPlayerShop(OfflinePlayer player) {
        if (!player.hasPlayedBefore()) return null;

        final Optional<PlayerShopEntity> unchecked = playerShopCache.getUnchecked(getName(player));
        if (!unchecked.isPresent()) return null;

        return unchecked.get();
    }

    public Map<String, Optional<PlayerShopEntity>> asShopMap() {
        return playerShopCache.asMap();
    }

    public String getName(OfflinePlayer player) {
        return player.getName().toLowerCase();
    }
}
