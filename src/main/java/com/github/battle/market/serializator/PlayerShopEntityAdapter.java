package com.github.battle.market.serializator;

import com.github.battle.core.serialization.ModelAdapter;
import com.github.battle.core.serialization.location.LocationText;
import com.github.battle.market.entity.PlayerShopEntity;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public final class PlayerShopEntityAdapter implements ModelAdapter<Optional<PlayerShopEntity>, ResultSet> {

    @Override
    public Optional<PlayerShopEntity> adaptModel(@Nullable ResultSet set) throws SQLException {
        if (set == null) return Optional.empty();

        final String rawLocation = set.getString("location");
        return PlayerShopEntity.builder()
          .id(set.getInt("id"))
          .owner(set.getString("owner").toLowerCase())
          .location(adaptLocation(rawLocation))
          .description(set.getString("description"))
          .createdAt(set.getTimestamp("created_at"))
          .build()
          .optional();
    }

    public Location adaptLocation(@Nullable String rawLocation) {
        if (rawLocation == null) return null;
        return LocationText.deserializeLocation(rawLocation);
    }
}
