package com.github.battle.market.job;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.PlayerShopEntityAdapter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public final class ShopEntitySync extends Thread {

    private final PlayerShopEntityAdapter entityAdapter;
    private final PlayerShopManager playerShopManager;
    private final MySQLRequester requester;
    private final MysqlBootstrap bootstrap;

    @Override
    public void run() {
        final Map<String, Optional<ShopEntity>> preLoadingCache = new HashMap<>();

        final Connection connection = Objects.requireNonNull(requester.getConnection());
        final String getAllShopInformation = bootstrap.getQuery("shop_information.get_all");

        try (PreparedStatement statement = connection.prepareStatement(getAllShopInformation)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    final String owner = resultSet.getString("owner");

                    final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(owner);
                    if (!offlinePlayer.hasPlayedBefore()) continue;

                    final Optional<ShopEntity> optional = entityAdapter.adaptModel(resultSet);
                    if (!optional.isPresent()) continue;

                    preLoadingCache.put(owner, optional);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        playerShopManager.putAllEntities(preLoadingCache);
    }
}
