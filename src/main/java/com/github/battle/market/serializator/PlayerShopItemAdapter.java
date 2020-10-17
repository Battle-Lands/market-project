package com.github.battle.market.serializator;

import com.github.battle.core.serialization.ModelAdapter;
import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.view.PlayerShopItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public final class PlayerShopItemAdapter implements ModelAdapter<List<PlayerShopItem>, Void> {

    private final PlayerShopManager playerShopManager;

    @Override
    public List<PlayerShopItem> adaptModel(Void instance) {
        final List<PlayerShopItem> playerShopItems = new ArrayList<>();
        for (Optional<PlayerShopEntity> optional : playerShopManager.getAllShopEntities()) {
            if (!optional.isPresent()) continue;

            // The bellow lines cause a unnecessary cache route, i need to fix this
            // But i have no idea how can i do this
            final PlayerShopEntity playerShopEntity = optional.get();

            final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerShopEntity.getOwner());
            if (offlinePlayer.isBanned()) continue;

            playerShopItems.add(new PlayerShopItem(playerShopEntity));
        }

        return playerShopItems;
    }
}
