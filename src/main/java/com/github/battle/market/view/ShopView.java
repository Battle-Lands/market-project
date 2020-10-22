package com.github.battle.market.view;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.serializator.PlayerShopItemAdapter;
import lombok.NonNull;
import me.saiintbrisson.minecraft.PreRenderViewContext;
import me.saiintbrisson.minecraft.ViewContext;
import me.saiintbrisson.minecraft.ViewFrame;
import me.saiintbrisson.minecraft.ViewItem;
import me.saiintbrisson.minecraft.pagination.PaginatedView;
import me.saiintbrisson.minecraft.pagination.PaginatedViewContext;
import me.saiintbrisson.minecraft.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class ShopView extends PaginatedView<ShopEntity> {

    private final PlayerShopItemAdapter playerShopItemAdapter;
    public ShopView(@NonNull PlayerShopItemAdapter playerShopItemAdapter) {
        super(6, "Battle Shop");
        this.playerShopItemAdapter = playerShopItemAdapter;

        setPaginationSource(playerShopItemAdapter.adaptModel(null));
    }

    @Override
    protected List<ShopEntity> onPaginationRender(PaginatedViewContext paginatedViewContext) {
        return playerShopItemAdapter.adaptModel(null);
    }

    @Override
    protected void onOpen(PreRenderViewContext context) {
        context.setInventoryTitle("opened inventory");
    }

    @Override
    protected void onClose(ViewContext context) {
        context.getPlayer().sendMessage("fuck you");
    }

    @Override
    protected void onPaginationItemRender(PaginatedViewContext paginatedViewContext, ViewItem viewItem, ShopEntity shopEntity) {

        System.out.println("called");
        final ItemStack itemStack = ItemBuilder
          .create(Material.SKULL_ITEM)
          .skull(shopEntity.getOwner())
          .build();

        viewItem.withItem(itemStack);
    }
}
