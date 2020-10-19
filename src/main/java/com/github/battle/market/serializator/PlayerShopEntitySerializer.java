package com.github.battle.market.serializator;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.core.serialization.ModelSerializer;
import com.github.battle.core.serialization.location.LocationText;
import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RequiredArgsConstructor
public final class PlayerShopEntitySerializer implements ModelSerializer<PlayerShopEntity> {

    private final MySQLRequester requester;
    private final MysqlBootstrap bootstrap;

    @Override
    public void serializeModel(PlayerShopEntity playerShopEntity) {
        if (playerShopEntity == null) return;

        final String rawLocation = serializeLocation(playerShopEntity.getLocation());
        final String description = playerShopEntity.getDescription();
        final String ownerLower = playerShopEntity.getOwner().toLowerCase();

        final int updateResult = requester.executeUpdate(
          bootstrap.getQuery("shop_information.update"),
          rawLocation,
          description,
          ownerLower
        );

        if(updateResult == 0) {
            requester.execute(
              bootstrap.getQuery("shop_information.insert"),
              ownerLower,
              rawLocation,
              description
            );
        }
    }

    public String serializeLocation(@Nullable Location location) {
        if (location == null) return null;
        return LocationText.serializeLocation(location);
    }
}
