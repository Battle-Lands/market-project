package com.github.battle.market.event.update;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.event.ShopUpdateEvent;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopCreatedEvent extends ShopUpdateEvent<Integer> {

    public ShopCreatedEvent(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        super(shopEntity, player, UpdateType.CREATED);
    }
}