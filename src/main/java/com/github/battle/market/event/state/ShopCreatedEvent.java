package com.github.battle.market.event.state;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.entity.shop.ShopState;
import com.github.battle.market.event.ShopUpdateEvent;
import com.github.battle.market.event.update.UpdateType;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopCreatedEvent extends ShopUpdateEvent<ShopState> {

    public ShopCreatedEvent(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        super(shopEntity, player, UpdateType.CREATED);
    }
}