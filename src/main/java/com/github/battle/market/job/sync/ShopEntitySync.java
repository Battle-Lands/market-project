package com.github.battle.market.job.sync;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.shop.PlayerShopEntityAdapter;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public final class ShopEntitySync extends Thread {

    private final PlayerShopEntityAdapter entityAdapter;
    private final PlayerShopManager playerShopManager;
    private final MysqlBootstrap bootstrap;

    @Override
    public void run() {
        final Map<String, Optional<ShopEntity>> preLoadingCache = new HashMap<>();

        final Connection connection = Objects.requireNonNull(bootstrap.getRequester().getConnection());
        final String getAllShopInformation = bootstrap.getQuery("shop_information.get_all");

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(getAllShopInformation)) {
                while (resultSet.next()) {
                    final Optional<ShopEntity> optional = entityAdapter.adaptModel(resultSet);
                    if (!optional.isPresent()) continue;

                    preLoadingCache.put(optional.get().getOwner(), optional);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        playerShopManager.putAllEntities(preLoadingCache);
    }
}
