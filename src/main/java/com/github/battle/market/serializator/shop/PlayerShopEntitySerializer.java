package com.github.battle.market.serializator.shop;

import com.github.battle.core.serialization.ModelSerializer;
import com.github.battle.core.serialization.location.text.LocationText;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.entity.ShopState;
import com.github.battle.market.manager.bootstrap.MysqlBootstrap;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public final class PlayerShopEntitySerializer implements ModelSerializer<ShopEntity> {

    private final MysqlBootstrap bootstrap;

    @Override
    public void serializeModel(ShopEntity playerShopEntity) {
        if (playerShopEntity == null) return;

        final String rawLocation = serializeLocation(playerShopEntity.getLocation());
        final String description = playerShopEntity.getDescription();
        final String ownerLower = playerShopEntity.getOwner().toLowerCase();

        final int updateResult = bootstrap.executeUpdate(
          "shop_information.update",
          rawLocation,
          description,
          ShopState.getStateName(playerShopEntity.getState()),
          ownerLower
        );

        if (updateResult == 0) {
            bootstrap.executeUpdate(
              "shop_information.insert",
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
