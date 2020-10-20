package com.github.battle.market.event.update;

import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.event.ShopEvent;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class ShopUpdateEvent extends ShopEvent {

    private final @NonNull PlayerShopEntity playerShopEntity;
    private final @NonNull UpdateType type;

    public enum UpdateType {
        UPDATED_LOCATION,
        UPDATED_DESCRIPTION,
        REMOVED_DESCRIPTION
    }
}
