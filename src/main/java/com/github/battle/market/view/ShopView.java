package com.github.battle.market.view;

import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.serializator.PlayerShopItemAdapter;
import com.github.battle.market.serializator.PlayerShopItemTemplate;
import me.saiintbrisson.minecraft.ViewItem;
import me.saiintbrisson.minecraft.pagination.PaginatedView;
import me.saiintbrisson.minecraft.pagination.PaginatedViewContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ShopView extends PaginatedView<ShopEntity> {

    private final PlayerShopItemTemplate playerShopItemTemplate;
    private final PlayerShopManager playerShopManager;
    public ShopView(PlayerShopItemTemplate playerShopItemTemplate, PlayerShopManager playerShopManager) {
        super(6, "Battle Shop");
        this.playerShopItemTemplate = playerShopItemTemplate;
        this.playerShopManager = playerShopManager;
    }

    @Override
    protected List<ShopEntity> onPaginationRender(PaginatedViewContext paginatedViewContext) {
        final List<ShopEntity> shopEntities = new ArrayList<>();
        for (Optional<ShopEntity> optional : playerShopManager.getAllShopEntities()) {
            optional.ifPresent(shopEntities::add);
        }
        return shopEntities;
    }

    @Override
    protected void onPaginationItemRender(PaginatedViewContext paginatedViewContext, ViewItem viewItem, ShopEntity shopEntity) {
        System.out.println("what?");
    }
}
