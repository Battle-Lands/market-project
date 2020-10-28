package com.github.battle.market.serializator.item;

import com.github.battle.core.serialization.ModelAdapter;
import com.github.battle.market.entity.ShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.view.PlayerShopItem;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public final class PlayerShopItemAdapter implements ModelAdapter<List<PlayerShopItem>, Void> {

    public static PlayerShopItemTemplate ITEM_TEMPLATE;

    private final PlayerShopItemTemplate playerShopItemTemplate;
    private final PlayerShopManager playerShopManager;

    public PlayerShopItemAdapter(@NonNull PlayerShopManager playerShopManager, Plugin plugin) {
        this.playerShopItemTemplate = new PlayerShopItemTemplate(plugin.getConfig());
        this.playerShopManager = playerShopManager;

        ITEM_TEMPLATE = playerShopItemTemplate;
    }

    @Override
    public List<PlayerShopItem> adaptModel(Void instance) {
        final List<PlayerShopItem> playerShopItems = new ArrayList<>();
        for (Optional<ShopEntity> optional : playerShopManager.getAllShopEntities()) {
            if (!optional.isPresent()) continue;

            // The bellow lines cause a unnecessary cache route, i need to fix this
            // But i have no idea how can i do this
            final ShopEntity shopEntity = optional.get();
            if (!playerShopManager.checkPlayerCondition(shopEntity.getPlayer()) || !shopEntity.isAccessible()) continue;

            playerShopItems.add(new PlayerShopItem(shopEntity));
        }

        return playerShopItems;
    }
}
