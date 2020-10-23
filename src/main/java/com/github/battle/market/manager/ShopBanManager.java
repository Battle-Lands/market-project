package com.github.battle.market.manager;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.market.entity.ShopBanEntity;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.job.ShopBanQueue;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.ban.ShopBanAdapter;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public ShopBanManager(@NonNull ShopBanQueue shopBanQueue) {
        this.entryBanEntities = new HashMap<>();
        this.shopBanAdapter = new ShopBanAdapter();
        this.shopBanQueue = shopBanQueue;
        this.bootstrap = shopBanQueue.getBootstrap();
    }

    public void addShopBan(@NonNull ShopBanEntity shopBanEntity) {
        final EntryBanEntity entryBan = getEntryBan(shopBanEntity.getPunishmentId());
        shopBanQueue.addBanEntityToQueue(shopBanEntity);
    }

    private EntryBanEntity getEntryBan(@NonNull int id) {
        EntryBanEntity entryBanEntity = entryBanEntities.get(id);
        if(entryBanEntity != null) return entryBanEntity;

        entryBanEntity = new EntryBanEntity(id);
        entryBanEntities.put(id, entryBanEntity);

        return entryBanEntity;
    }

    @Data
    private static final class EntryBanEntity {

        private final List<ShopBanEntity> shopBanEntities;
        private final int id;

        public EntryBanEntity(@NonNull int id, @NonNull MysqlBootstrap bootstrap) {
            this.id = id;
            this.shopBanEntities = new ArrayList<>();

            syncEntities(bootstrap);
        }

        private void syncEntities(@NonNull MysqlBootstrap bootstrap, @NonNull ShopBanAdapter shopBanAdapter) {
            final Connection connection = bootstrap.getRequester().getConnection();

            final String getAllById = bootstrap.getQuery("shop_ban.get_by_id");
            try (PreparedStatement statement = connection.prepareStatement(getAllById)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        addBanEntity(shopBanAdapter.adaptModel(resultSet));
                    }
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        public void addBanEntity(@NonNull ShopBanEntity shopBanEntity) {
            shopBanEntities.add(shopBanEntity);
        }
    }
}
