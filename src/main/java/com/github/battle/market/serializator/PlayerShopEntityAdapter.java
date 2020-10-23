package com.github.battle.market.serializator;

import com.github.battle.core.serialization.ModelAdapter;
import com.github.battle.core.serialization.location.text.LocationText;
import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.entity.ShopState;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public final class PlayerShopEntityAdapter implements ModelAdapter<Optional<ShopEntity>, ResultSet> {

    @Override
    public Optional<ShopEntity> adaptModel(@Nullable ResultSet set) throws SQLException {
        if (set == null) return Optional.empty();

        final String rawLocation = set.getString("location");
        final String rawState = set.getString("state");

        return PlayerShopEntity.builder()
          .id(set.getInt("id"))
          .owner(set.getString("owner").toLowerCase())
          .location(adaptLocation(rawLocation))
          .description(set.getString("description"))
          .createdAt(set.getTimestamp("created_at"))
          .state(ShopState.getStateByName(rawState))
          .build()
          .optional();
    }

    public Location adaptLocation(@Nullable String rawLocation) {
        if (rawLocation == null) return null;
        return LocationText.deserializeLocation(rawLocation);
    }
}
