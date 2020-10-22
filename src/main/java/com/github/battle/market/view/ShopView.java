package com.github.battle.market.view;

import com.github.battle.core.plugin.PluginCore;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.serializator.PlayerShopItemAdapter;
import com.github.battle.market.serializator.PlayerShopItemTemplate;
import lombok.NonNull;
import me.saiintbrisson.minecraft.*;
import me.saiintbrisson.minecraft.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class ShopView extends PaginatedView<ShopEntity> {

    private final PlayerShopItemAdapter playerShopItemAdapter;
    private final PlayerShopItemTemplate itemTemplate;
    public ShopView(@NonNull PluginCore plugin, @NonNull PlayerShopItemAdapter playerShopItemAdapter) {
        super(6, "Battle Shop");
        this.itemTemplate = new PlayerShopItemTemplate(plugin);
        this.playerShopItemAdapter = playerShopItemAdapter;

        setCancelOnClick(true);
        setupViewFrame(plugin);
    }

    private void setupViewFrame(@NonNull PluginCore pluginCore) {
        final ViewFrame viewFrame = pluginCore.getViewFrameService();
        viewFrame.addView(this);

        if(viewFrame.getListener() == null) {
            viewFrame.register();
        }
    }

    @Override
    protected void onOpen(PreRenderViewContext context) {
        setPaginationSource(playerShopItemAdapter.adaptModel(null));

        final Player player = context.getPlayer();
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 3F, 1F);
    }

    @Override
    protected void onPaginationItemRender(PaginatedViewContext paginatedViewContext, ViewItem viewItem, ShopEntity shopEntity) {
        final OfflinePlayer offlinePlayer = shopEntity.getPlayer();

        viewItem.withItem(ItemBuilder
          .create(Material.SKULL_ITEM)
          .skull(shopEntity.getOwner())
          .name(itemTemplate.getItemBaseName(offlinePlayer))
          .lore(itemTemplate.getItemBaseLore(offlinePlayer))
          .build()
        );
    }
}
