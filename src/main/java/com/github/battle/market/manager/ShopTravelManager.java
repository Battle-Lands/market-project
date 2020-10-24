package com.github.battle.market.manager;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.exception.ShopTravelException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public final class ShopTravelManager {

    private final static Material[] BLACKLIST_MATERIAL = new Material[]{};

    private final PlayerShopManager playerShopManager;

    public void travelPlayerShop(@NonNull Player player, @NonNull ShopEntity shopEntity) throws ShopTravelException {

    }

    public boolean checkIfShopLocationIsTrust(@NonNull ShopEntity shopEntity) {
        final Location location = shopEntity.getLocation();
        if (location == null) return false;

        final int minX = location.getBlockX() - 3;

        /*for (int x = (location.getBlockX() - 3); x < ; x++) {

        } */
        return true;
    }
}

