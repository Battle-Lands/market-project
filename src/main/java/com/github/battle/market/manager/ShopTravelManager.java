package com.github.battle.market.manager;

import com.github.battle.core.builder.Cuboid;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.exception.ShopTravelException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredArgsConstructor
public final class ShopTravelManager {

    private final static Material[] BLACKLIST_MATERIAL = new Material[]{};

    private final PlayerShopManager playerShopManager;

    public void travelPlayerShop(@NonNull Player player, @NonNull ShopEntity shopEntity) throws ShopTravelException {
        final boolean isTrust = checkIfShopLocationIsTrust(player, shopEntity);
    }

    public boolean checkIfShopLocationIsTrust(@NonNull Player player, @NonNull ShopEntity shopEntity) {
        final Location location = shopEntity.getLocation();
        if (location == null) return false;

        final Location subtract = location.clone().subtract(0, 1, 0);
        final List<Block> blocks = Cuboid.listAroundPlatform(subtract, 1);
        for (Block block : blocks) {
            block.setType(Material.GLASS);
        }

        return true;
    }
}

