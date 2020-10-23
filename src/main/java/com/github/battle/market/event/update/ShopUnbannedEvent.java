package com.github.battle.market.event.update;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.entity.ShopState;
import com.github.battle.market.event.ShopBanEvent;
import com.github.battle.market.event.ShopUpdateEvent;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopUnbannedEvent extends ShopBanEvent {

    public ShopUnbannedEvent(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        super(shopEntity, player, UpdateType.UNBANNED);
    }
}