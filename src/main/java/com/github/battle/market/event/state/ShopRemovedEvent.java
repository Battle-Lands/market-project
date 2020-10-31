package com.github.battle.market.event.state;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.entity.ShopState;
import com.github.battle.market.event.update.ShopUpdateEvent;
import com.github.battle.market.event.update.UpdateType;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopRemovedEvent extends ShopUpdateEvent<ShopState> {

    public ShopRemovedEvent(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        super(shopEntity, player, UpdateType.REMOVED);
    }
}
