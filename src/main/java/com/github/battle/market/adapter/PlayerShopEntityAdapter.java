package com.github.battle.market.adapter;

import com.github.battle.core.common.ModelAdapter;
import com.github.battle.core.serialization.location.LocationText;
import com.github.battle.market.entity.PlayerShopEntity;
import lombok.NonNull;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public final class PlayerShopEntityAdapter implements ModelAdapter<Optional<PlayerShopEntity>, ResultSet> {

    @Override
    public Optional<PlayerShopEntity> adaptModel(@Nullable ResultSet set) throws SQLException {
        if(set == null) return Optional.empty();

        //final Location deserializeLocation = LocationText.deserializeLocation(set.getString("location"));

        return PlayerShopEntity.builder()
          .id(set.getInt("id"))
          .owner(set.getString("owner"))
          .description(set.getString("description"))
          .createdAt(set.getTimestamp("created_at"))
          //.location(deserializeLocation)
          .build()
          .optional();
    }
}
