package com.github.battle.market.exception.travel;

import com.github.battle.market.exception.ShopTravelException;

public final class ObstructedTravelException extends ShopTravelException {

    public ObstructedTravelException() {
        super("No solid floor has defined.");
    }
}
