package com.github.battle.market.view;

import com.github.battle.market.serializator.PlayerShopItemAdapter;
import me.saiintbrisson.minecraft.paginator.PaginatedItemConsumer;
import me.saiintbrisson.minecraft.paginator.PaginatedView;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public final class ShopView extends PaginatedView<PlayerShopItem> implements PaginatedItemConsumer<PlayerShopItem> {

    public ShopView(Plugin owner, PlayerShopItemAdapter playerShopItemAdapter) {
        super(owner, "Battle Shop", new String[]{
            "000000000",
            "011111110",
            "011111110",
            "011111110",
            "000<0>000"
          }, () -> playerShopItemAdapter.adaptModel(null)
        );

        setUpdateAfterClick(true);
        setItemProcessor(this);
    }

    @Override
    public void process(Player player, PlayerShopItem shopItem, InventoryClickEvent event) {

    }
}
