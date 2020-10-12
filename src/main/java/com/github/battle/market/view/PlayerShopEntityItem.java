package com.github.battle.market.view;

import com.github.battle.market.entity.PlayerShopEntity;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.ItemBuilder;
import me.saiintbrisson.minecraft.paginator.PaginatedItem;
import me.saiintbrisson.minecraft.paginator.PaginatedViewHolder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor
public final class PlayerShopEntityItem implements PaginatedItem {

    private final PlayerShopEntity shopEntity;

    @Override
    public ItemStack toItemStack(Player player, PaginatedViewHolder paginatedViewHolder) {
        return new ItemBuilder(Material.SKULL_ITEM)
          .lore(
            "§7Visite a loja de " + shopEntity.getName(),
            "§7description " + (
              shopEntity.getDescription() != null
                ? shopEntity.getDescription()
                : "sla"
            )
          )
          .skullOwner(shopEntity.getName())
          .build();
    }
}
