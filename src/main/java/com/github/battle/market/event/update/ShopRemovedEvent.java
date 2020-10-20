package com.github.battle.market.event.update;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.event.ShopUpdateEvent;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopRemovedEvent extends ShopUpdateEvent<Integer> {

    public ShopRemovedEvent(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        super(shopEntity, player, UpdateType.REMOVED);
    }
}
