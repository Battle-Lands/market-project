package com.github.battle.market.view;

import com.github.battle.market.entity.PlayerShopEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.saiintbrisson.minecraft.ItemBuilder;
import me.saiintbrisson.minecraft.paginator.PaginatedItem;
import me.saiintbrisson.minecraft.paginator.PaginatedViewHolder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter
@RequiredArgsConstructor
public final class PlayerShopItem implements PaginatedItem {

    private final PlayerShopEntity playerShopEntity;

    @Override
    public ItemStack toItemStack(Player player, PaginatedViewHolder paginatedViewHolder) {
        final String owner = playerShopEntity.getOwner();
        final String description = playerShopEntity.getDescription();

        return new ItemBuilder(Material.SKULL_ITEM)
          .lore(
            "§7Visite a loja de " + owner,
            "§7description " + (
              description != null
                ? description
                : "sla"
            )
          )
          .skullOwner(owner)
          .build();
    }
}
