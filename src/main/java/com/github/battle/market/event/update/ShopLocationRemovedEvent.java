package com.github.battle.market.event.update;

import com.github.battle.market.entity.ShopEntity;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class ShopLocationRemovedEvent extends ShopUpdateEvent<Location> {

    public ShopLocationRemovedEvent(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        super(shopEntity, player, UpdateType.REMOVED_LOCATION);
    }
}
