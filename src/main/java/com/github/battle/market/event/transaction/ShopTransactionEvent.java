package com.github.battle.market.event.transaction;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.event.ShopEvent;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;

@Getter
public abstract class ShopTransactionEvent extends ShopEvent {

    public ShopTransactionEvent(@NonNull ShopEntity shopEntity, @NonNull Player player) {
        super(shopEntity, player);
    }
}
