package com.github.battle.market.view;

import me.saiintbrisson.minecraft.paginator.PaginatedView;
import org.bukkit.plugin.Plugin;

public final class ShopPaginatedView extends PaginatedView<PlayerShopEntityItem> {

    public ShopPaginatedView(Plugin owner, PlayerShopEntityAdapter playerShopEntityAdapter) {
        super(owner, "Battle Shop", new String[]{
            "000000000",
            "011111110",
            "011111110",
            "011111110",
            "000<0>000"
          }, playerShopEntityAdapter::adaptPlayerShopItemEntity
        );

        setItemProcessor((player, playerShopEntityItem, inventoryClickEvent) -> {
            inventoryClickEvent.setCancelled(true);
            player.sendMessage("message");
        });
    }
}
