package com.github.battle.market.bootstrap;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.serializator.PlayerShopEntityAdapter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public final class MysqlBootstrap {

    private final MySQLRequester requester;
    private final ConfigurationSection bootstrapQueries;

    public MysqlBootstrap(@NonNull FileConfiguration configuration, @NonNull MySQLRequester requester) {
        this.requester = requester;
        this.bootstrapQueries = configuration.getConfigurationSection("bootstrap.queries");
    }

    public MysqlBootstrap createInitialTables() {
        requester.execute(
          getQuery("create_shop_information_table")
        );
        return this;
    }

    public Map<String, Optional<PlayerShopEntity>> getAllPlayerShopEntities(@NonNull PlayerShopEntityAdapter playerShopEntityAdapter) {
        final Map<String, Optional<PlayerShopEntity>> preLoadingCache = new HashMap<>();

        final Connection connection = requester.getConnection();
        final String getAllShopInformation = getQuery("getall_shop_information");

        try (PreparedStatement statement = connection.prepareStatement(getAllShopInformation)) {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final Optional<PlayerShopEntity> optional = playerShopEntityAdapter.adaptModel(resultSet);
                if (!optional.isPresent()) continue;

                final String owner = optional.get().getOwner();
                final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(owner);
                if(!offlinePlayer.hasPlayedBefore()) continue;

                preLoadingCache.put(
                  owner,
                  optional
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return preLoadingCache;
    }

    public String getQuery(@NonNull String sectionPath) {
        return String.join(
          "",
          bootstrapQueries.getStringList(sectionPath)
        );
    }
}
