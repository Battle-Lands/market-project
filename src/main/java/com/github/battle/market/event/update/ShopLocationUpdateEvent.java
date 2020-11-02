package com.github.battle.market.event.update;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.event.ShopUpdateEvent;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class ShopLocationUpdateEvent extends ShopUpdateEvent<Location> {

    public ShopLocationUpdateEvent(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        super(shopEntity, player, UpdateType.UPDATED_LOCATION);
    }
}
