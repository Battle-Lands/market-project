package com.github.battle.market.event;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.event.update.UpdateType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
public abstract class ShopUpdateEvent<T> extends ShopEvent {

    private final UpdateType type;

    @Setter
    private T oldValue, newValue;

    public ShopUpdateEvent(@NonNull ShopEntity shopEntity, @NonNull Player player, @NonNull UpdateType type) {
        super(shopEntity, player);
        this.type = type;
    }
}
