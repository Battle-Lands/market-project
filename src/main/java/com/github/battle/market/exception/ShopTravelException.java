package com.github.battle.market.exception;

import lombok.NonNull;

public class ShopTravelException extends IllegalStateException {

    public ShopTravelException(@NonNull String cause) {
        super(cause);
    }
}
