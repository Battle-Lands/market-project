package com.github.battle.market.manager;

import com.github.battle.market.cache.PlayerShopCacheLoader;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.entity.shop.PlayerShopEntity;
import com.github.battle.market.exception.ShopTravelException;
import com.github.battle.market.job.sync.ShopEntitySync;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.shop.PlayerShopEntityAdapter;
import com.github.battle.market.serializator.shop.PlayerShopEntitySerializer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("all")
public final class PlayerShopManager {

    private final LoadingCache<String, Optional<ShopEntity>> playerShopCache;
    private final PlayerShopEntitySerializer playerShopEntitySerializer;
    private final PlayerShopEntityAdapter playerShopEntityAdapter;
    private final ShopTravelManager shopTravelManager;
    private final MysqlBootstrap bootstrap;

    @Getter
    private final ShopEntitySync entitySync;

    public PlayerShopManager(@NonNull MysqlBootstrap bootstrap) {
        this.playerShopEntitySerializer = new PlayerShopEntitySerializer(bootstrap);
        this.playerShopEntityAdapter = new PlayerShopEntityAdapter();
        this.shopTravelManager = new ShopTravelManager(this);
        this.bootstrap = bootstrap;

        final PlayerShopCacheLoader loader = new PlayerShopCacheLoader(
          playerShopEntityAdapter,
          bootstrap
        );

        this.playerShopCache = CacheBuilder
          .newBuilder()
          .build(loader);

        this.entitySync = new ShopEntitySync(
          playerShopEntityAdapter,
          this,
          bootstrap
        );
    }

    public void updatePlayerShop(OfflinePlayer player, @NonNull ShopEntity shopEntity) {
        if (!checkPlayerCondition(player)) return;
        playerShopCache.put(
          player.getName(),
          shopEntity.optional()
        );
    }

    public ShopEntity refreshPlayerShop(OfflinePlayer player) {
        final ShopEntity shopEntity = getPlayerShop(player);
        if (shopEntity == null) return null;

        playerShopEntitySerializer.serializeModel(shopEntity);
        if (shopEntity.isCreated()) {
            playerShopCache.refresh(player.getName());
        }

        return getPlayerShop(player);
    }

    public ShopEntity getEmptyShopEntity(OfflinePlayer player) {
        if (!checkPlayerCondition(player)) return null;
        return PlayerShopEntity
          .builder()
          .owner(player.getName())
          .created(true)
          .build();
    }

    public boolean checkPlayerCondition(OfflinePlayer player) {
        return player != null && (player.isOnline() || player.getLastPlayed() != 0);
    }

    public ShopEntity getLazyPlayerShop(OfflinePlayer player) {
        final ShopEntity playerShop = getPlayerShop(player);
        if (playerShop != null) {
            return playerShop.canInitialize()
              ? playerShop
              : null;
        }

        final ShopEntity emptyShopEntity = getEmptyShopEntity(player);
        updatePlayerShop(player, emptyShopEntity);
        return emptyShopEntity;
    }

    public ShopEntity getPlayerShop(OfflinePlayer player) {
        if (!checkPlayerCondition(player)) return null;

        final Optional<ShopEntity> unchecked = playerShopCache.getUnchecked(player.getName());
        if (!unchecked.isPresent()) return null;

        return unchecked.get();
    }

    public ShopEntity getCheckedPlayerShop(OfflinePlayer player) {
        final ShopEntity shopEntity = getPlayerShop(player);
        if (shopEntity == null || !shopEntity.isAccessible()) return null;

        return shopEntity;
    }

    public boolean hasNonShopSet() {
        return getAllShopEntities().isEmpty();
    }

    public void putAllEntities(Map<String, Optional<ShopEntity>> optionalMap) {
        asShopMap().putAll(optionalMap);
    }

    public boolean hasPlayerShop(OfflinePlayer player) {
        return getCheckedPlayerShop(player) != null;
    }

    public int getPlayerShopSize() {
        return asShopMap().size();
    }

    public List<Optional<ShopEntity>> getAllShopEntities() {
        return new ArrayList<>(asShopMap().values());
    }

    public Map<String, Optional<ShopEntity>> asShopMap() {
        return playerShopCache.asMap();
    }

    public void travelPlayerShop(@NonNull Player player, @NonNull ShopEntity shopEntity) throws ShopTravelException {
        shopTravelManager.travelPlayerShop(player, shopEntity);
    }
}
