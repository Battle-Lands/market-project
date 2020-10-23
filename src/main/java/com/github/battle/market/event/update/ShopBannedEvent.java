package com.github.battle.market.event.update;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.event.ShopBanEvent;
import lombok.NonNull;
import org.bukkit.entity.Player;

public final class ShopBannedEvent extends ShopBanEvent {

    public ShopBannedEvent(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        super(shopEntity, player, UpdateType.BANNED);
    }
}
