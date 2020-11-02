package com.github.battle.market.entity.shop;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShopState {
    ACCESSIBLE(true, true),
    REMOVED(false, true),
    BANNED(false, false);

    private final boolean accessible;
    private final boolean canInitialize;

    public static ShopState getStateByName(@NonNull String name) {
        for (ShopState value : values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    public static String getStateName(ShopState state) {
        return state.name();
    }
}
