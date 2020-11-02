package com.github.battle.market.job.sync;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.market.entity.ban.ShopBanEntity;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import com.github.battle.market.serializator.ban.ShopBanAdapter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public final class ShopBanSync {

    private final ShopBanAdapter shopBanAdapter;
    private final MysqlBootstrap bootstrap;

    public List<ShopBanEntity> sync(@NonNull int id) {
        final MySQLRequester requester = bootstrap.getRequester();
        final Connection connection = Objects.requireNonNull(requester.getConnection());

        final List<ShopBanEntity> shopBanEntities = new ArrayList<>();

        final String getAllById = bootstrap.getQuery("shop_ban.get_by_id");
        try (PreparedStatement statement = connection.prepareStatement(getAllById)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    shopBanEntities.add(shopBanAdapter.adaptModel(resultSet));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return shopBanEntities;
    }
}
