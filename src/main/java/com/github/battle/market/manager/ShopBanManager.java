package com.github.battle.market.manager;

import com.github.battle.market.entity.ShopBanEntity;
import com.github.battle.market.job.ShopBanQueue;
import com.github.battle.market.job.ShopBanSync;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.ban.ShopBanAdapter;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public final class ShopBanManager {

    private final Map<Integer, EntryBanEntity> entryBanEntities;
    private final ShopBanAdapter shopBanAdapter;
    private final ShopBanQueue shopBanQueue;
    private final MysqlBootstrap bootstrap;
    private final ShopBanSync shopBanSync;

    public ShopBanManager(@NonNull ShopBanQueue shopBanQueue) {
        this.entryBanEntities = new HashMap<>();
        this.shopBanAdapter = new ShopBanAdapter();
        this.shopBanQueue = shopBanQueue;
        this.bootstrap = shopBanQueue.getBootstrap();
        this.shopBanSync = new ShopBanSync(shopBanAdapter, bootstrap);
    }

    public void addShopBan(@NonNull ShopBanEntity shopBanEntity) {
        getEntryBan(shopBanEntity.getPunishmentId())
          .addBanEntity(shopBanEntity);

        shopBanQueue.addBanEntityToQueue(shopBanEntity);
    }

    public List<ShopBanEntity> getShopBanEntities(@NonNull int id) {
        return getEntryBan(id).getShopBanEntities();
    }

    public ShopBanManager removeEntryBan(@NonNull int id) {
        entryBanEntities.remove(id);
        return this;
    }

    private EntryBanEntity getEntryBan(@NonNull int id) {
        EntryBanEntity entryBanEntity = entryBanEntities.get(id);
        if (entryBanEntity != null) return entryBanEntity;

        entryBanEntity = new EntryBanEntity(id).syncEntities(shopBanSync);
        entryBanEntities.put(id, entryBanEntity);

        return entryBanEntity;
    }

    @Data
    private static final class EntryBanEntity {

        private final List<ShopBanEntity> shopBanEntities;
        private final int id;

        public EntryBanEntity(@NonNull int id) {
            this.id = id;
            this.shopBanEntities = new ArrayList<>();
        }

        public EntryBanEntity syncEntities(@NonNull ShopBanSync shopBanSync) {
            shopBanEntities.addAll(shopBanSync.sync(id));
            return this;
        }

        public void addBanEntity(@NonNull ShopBanEntity shopBanEntity) {
            shopBanEntities.add(shopBanEntity);
        }
    }
}
