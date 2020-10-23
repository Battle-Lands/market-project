package com.github.battle.market.view;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.serializator.PlayerShopItemAdapter;
import com.github.battle.market.serializator.PlayerShopItemTemplate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.ItemBuilder;
import me.saiintbrisson.minecraft.paginator.PaginatedItem;
import me.saiintbrisson.minecraft.paginator.PaginatedViewHolder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor
public final class PlayerShopItem implements PaginatedItem {

    private final static PlayerShopItemTemplate ITEM_TEMPLATE = PlayerShopItemAdapter.ITEM_TEMPLATE;

    private final ShopEntity shopEntity;

    @Override
    public ItemStack toItemStack(Player player, PaginatedViewHolder paginatedViewHolder) {
        final OfflinePlayer offlinePlayer = shopEntity.getPlayer();

        return new ItemBuilder(Material.SKULL_ITEM)
          .name(ITEM_TEMPLATE.getItemBaseName(offlinePlayer))
          .lore(ITEM_TEMPLATE.getItemBaseLore(offlinePlayer))
          .skullOwner(offlinePlayer.getName())
          .build();
    }
}
