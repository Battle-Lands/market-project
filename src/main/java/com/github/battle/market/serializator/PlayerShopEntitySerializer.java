package com.github.battle.market.serializator;

import com.github.battle.core.database.requester.MySQLRequester;
import com.github.battle.core.serialization.ModelSerializer;
import com.github.battle.core.serialization.location.LocationText;
import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public final class PlayerShopEntitySerializer implements ModelSerializer<PlayerShopEntity> {

    private final MySQLRequester requester;
    private final MysqlBootstrap bootstrap;

    @Override
    public void serializeModel(PlayerShopEntity playerShopEntity) {
        if (playerShopEntity == null) return;

        final String updateShopInformation = bootstrap.getQuery("shop_information.update");
        final Location location = playerShopEntity.getLocation();

        requester.execute(
          updateShopInformation,
          playerShopEntity.getOwner().toLowerCase(),
          serializeLocation(location),
          playerShopEntity.getDescription()
        );
    }

    public String serializeLocation(@Nullable Location location) {
        if (location == null) return null;
        return LocationText.serializeLocation(location);
    }
}
