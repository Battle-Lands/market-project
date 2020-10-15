package com.github.battle.market.serializator;

import com.github.battle.core.serialization.ModelAdapter;
import com.github.battle.market.entity.PlayerShopEntity;
import com.github.battle.market.manager.PlayerShopManager;
import com.github.battle.market.view.PlayerShopItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public final class PlayerShopItemAdapter implements ModelAdapter<List<PlayerShopItem>, Void> {

    private final PlayerShopManager playerShopManager;

    private final List<PlayerShopItem> playerShopItems = new ArrayList<>();

    @Override
    public List<PlayerShopItem> adaptModel(Void instance) {
        if (!playerShopItems.isEmpty() && playerShopItems.size() == playerShopManager.getPlayerShopSize()) {
            return playerShopItems;
        }

        playerShopItems.clear();
        for (Optional<PlayerShopEntity> optional : playerShopManager.getAllShopEntities()) {
            if (!optional.isPresent()) continue;
            playerShopItems.add(new PlayerShopItem(optional.get()));
        }

        return playerShopItems;
    }
}
