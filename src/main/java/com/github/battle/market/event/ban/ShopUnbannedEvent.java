package com.github.battle.market.event.ban;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.event.update.UpdateType;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopUnbannedEvent extends ShopBanEvent {

    public ShopUnbannedEvent(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        super(shopEntity, player, UpdateType.UNBANNED);
    }
}