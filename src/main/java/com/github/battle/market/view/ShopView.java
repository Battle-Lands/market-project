package com.github.battle.market.view;

import com.github.battle.market.adapter.PlayerShopItemAdapter;
import me.saiintbrisson.minecraft.paginator.PaginatedView;
import org.bukkit.plugin.Plugin;

public final class ShopView extends PaginatedView<PlayerShopItem> {

    public ShopView(Plugin owner, PlayerShopItemAdapter playerShopItemAdapter) {
        super(owner, "Battle Shop", new String[]{
            "000000000",
            "011111110",
            "011111110",
            "011111110",
            "000<0>000"
          }, () -> playerShopItemAdapter.adaptModel(null)
        );

        setItemProcessor((player, playerShopItem, inventoryClickEvent) -> {
            playerShopItemAdapter.getPlayerShopManager().refleshPlayerShop(player);
            inventoryClickEvent.setCancelled(true);
        });
    }
}
