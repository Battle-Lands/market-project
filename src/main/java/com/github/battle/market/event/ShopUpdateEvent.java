package com.github.battle.market.event;

import com.github.battle.market.entity.ShopEntity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.entity.Player;

import java.sql.Timestamp;

@Getter
@ToString
public abstract class ShopUpdateEvent<T> extends ShopEvent {

    private @Setter T oldValue;
    private @Setter T newValue;

    private final Timestamp timestamp;
    private final UpdateType type;
    public ShopUpdateEvent(@NonNull ShopEntity shopEntity, @NonNull Player player, @NonNull UpdateType type) {
        super(shopEntity, player);
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.type = type;
    }

    public enum UpdateType {
        CREATED,
        REMOVED,
        UPDATED_LOCATION,
        REMOVED_LOCATION,
        UPDATED_DESCRIPTION,
        REMOVED_DESCRIPTION
    }
}
