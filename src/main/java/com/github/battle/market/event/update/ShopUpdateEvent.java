package com.github.battle.market.event.update;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.event.ShopEvent;
import com.github.battle.market.event.update.UpdateType;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.entity.Player;

import java.sql.Timestamp;

@Getter
public abstract class ShopUpdateEvent<T> extends ShopEvent {

    private final Timestamp timestamp;
    private final UpdateType type;

    @Setter
    private T oldValue, newValue;

    public ShopUpdateEvent(@NonNull ShopEntity shopEntity, @NonNull Player player, @NonNull UpdateType type) {
        super(shopEntity, player);
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.type = type;
    }
}
